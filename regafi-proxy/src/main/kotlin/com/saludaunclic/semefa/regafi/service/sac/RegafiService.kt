package com.saludaunclic.semefa.regafi.service.sac

import com.fasterxml.jackson.databind.ObjectMapper
import com.saludaunclic.semefa.regafi.service.mq.MqClientService
import com.saludaunclic.semefa.regafi.dto.SacIn997RegafiUpdate
import com.saludaunclic.semefa.regafi.mapper.RegoofyMapper
import com.saludaunclic.semefa.regafi.model.DataFrame
import com.saludaunclic.semefa.regafi.model.DataFrameStatus
import com.saludaunclic.semefa.regafi.model.DataFrameType
import com.saludaunclic.semefa.regafi.repository.DataFrameRepository
import com.saludaunclic.semefa.regafi.service.date.DateService
import com.saludaunclic.semefa.regafi.throwing.MqMaxAttemptReachedException
import com.saludaunclic.semefa.regafi.throwing.ServiceException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In271RegafiUpdate
import pe.gob.susalud.jr.transaccion.susalud.service.RegafiUpdate271Service
import pe.gob.susalud.jr.transaccion.susalud.service.RegafiUpdate997Service
import java.util.UUID

@Service
class RegafiService(
    private val regafiUpdate271Service: RegafiUpdate271Service,
    private val regafiUpdate997Service: RegafiUpdate997Service,
    private val regafiMapper: RegoofyMapper,
    private val mqClientService: MqClientService,
    private val errorsService: ErrorsService,
    private val dataFrameRepository: DataFrameRepository,
    private val dates: DateService,
    private val objectMapper: ObjectMapper
) {
    companion object {
        const val TAG_997: String = "txRespuesta"
        const val TX_NAME: String = "271_REGAFI_UPDATE"
    }

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun process271DataFrame(in271RegafiUpdate: In271RegafiUpdate): SacIn997RegafiUpdate =
        try {
            val update271 = processResponse(putAndGetMessage(processRequest(normalize(in271RegafiUpdate))))
            persistDataFrame(
                createDataFrame(update271.idMensaje, DataFrameStatus.PROCESSED)
                    .apply { correlativeId = update271.idCorrelativo })
            update271
        } catch (ex: MqMaxAttemptReachedException) {
            fallback997(ex, persistDataFrame(createDataFrame(ex.messageId, DataFrameStatus.PENDING)))
        }

    fun get271DataFrame(messageId: String): SacIn997RegafiUpdate {
        dataFrameRepository
            .findByMessageId(messageId)
            .ifPresent {
                if (it.status == DataFrameStatus.PROCESSED) {
                    throw ServiceException("Mensaje con id $messageId ya fue procesado", HttpStatus.NOT_FOUND)
                }
            }

        return try {
            val get271 = processResponse(mqClientService.fetchMessage(messageId))
            persistDataFrame(createDataFrame(messageId, DataFrameStatus.PROCESSED))
            get271
        } catch (ex: MqMaxAttemptReachedException) {
            fallback997(ex, persistDataFrame(createDataFrame(messageId, DataFrameStatus.PENDING)))
        }
    }

    private fun normalize(in271RegafiUpdate: In271RegafiUpdate): In271RegafiUpdate =
        in271RegafiUpdate.apply {
            if (idCorrelativo == null) {
                idCorrelativo = UUID.randomUUID().toString()
            }
            val now = dates.now()
            in271RegafiUpdate.feTransaccion = dates.formatDate(now)
            in271RegafiUpdate.hoTransaccion = dates.formatTime(now)
        }

    private fun createDataFrame(messageId: String, status: DataFrameStatus): DataFrame =
        dataFrameRepository
            .findByMessageId(messageId)
            .orElse(
                DataFrame().apply {
                    this.messageId = messageId
                    type = DataFrameType.UPDATE_271
                    this.status = status
                    processDate = dates.nowTimestamp()
                }.apply {
                    attempts += 1
                })

    private fun persistDataFrame(dataFrame: DataFrame): DataFrame = dataFrameRepository.save(dataFrame)

    private fun fallback997(ex: MqMaxAttemptReachedException, dataFrame: DataFrame): SacIn997RegafiUpdate =
        SacIn997RegafiUpdate(ex.messageId)
            .apply {
                mensajeError = ex.message
                intentos = dataFrame.attempts
            }

    private fun parseRequest(in271RegafiUpdate: In271RegafiUpdate): String =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
        "<sus:Online271RegafiUpdateRequest " +
            "xmlns:sus=\"http://www.susalud.gob.pe/Afiliacion/Online271RegafiUpdateRequest.xsd\" " +
            "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" " +
            "xsi:schemaLocation=\"http://www.susalud.gob.pe/Afiliacion/Online271RegafiUpdateRequest.xsd../MsgSetProjOnline271RegafiUpdateRequest/importFiles/Online271RegafiUpdateRequest.xsd \">" +
            "<txNombre>$TX_NAME</txNombre>" +
            "<txPeticion>${regafiUpdate271Service.beanToX12N(in271RegafiUpdate)}</txPeticion>" +
        "</sus:Online271RegafiUpdateRequest>"

    private fun processRequest(in271RegafiUpdate: In271RegafiUpdate): String =
        with(in271RegafiUpdate) {
            if (logger.isDebugEnabled) {
                logger.debug("From bean to X12, bean: \n${objectMapper.writeValueAsString(this)}")
            }

            parseRequest(this).also {
                logger.debug("From bean to X12, X12: \n$it")
            }
        }

    private fun putAndGetMessage(dataFrame: String): Map<String, String> =
        with(dataFrame) {
            logger.info("=== Start MQ Connection ===")
            logger.debug("Sending X12 message with this data: $this")

            try {
                mqClientService.sendMessageSync(this)
                    .also { logger.info("=== End MQ Connection ===") }
            } catch (ex: Exception) {
                throw ServiceException("Error en comunicación con MQ", ex, HttpStatus.SERVICE_UNAVAILABLE)
            }
        }

    private fun processResponse(messageMap: Map<String, String>): SacIn997RegafiUpdate =
        with(messageMap) {
            val x12: String = extractX12(this[MqClientService.MESSAGE_KEY] ?: "", TAG_997)
            logger.debug("From X12 to bean, X12: \n$x12")

            val response: SacIn997RegafiUpdate = regafiMapper.toSac997Update(
                this[MqClientService.MESSAGE_ID_KEY] ?: "",
                regafiUpdate997Service
                    .x12NToBean(x12)
                    .apply { isFlag = true })
            response.in271RegafiUpdateExcepcion.forEach {
                it.errorCampo = errorsService.getFieldError(it.coCampoErr.toInt())
                it.errorCampoRegla = errorsService.getFieldErrorRule(it.inCoErrorEncontrado.toInt())
            }

            response.also {
                if (logger.isDebugEnabled) {
                    logger.debug("From X12 to bean, bean: \n${objectMapper.writeValueAsString(it)}")
                }
            }
        }

    private fun extractX12(xmlText: String, tag: String): String {
        logger.debug("Extracting X12 from $xmlText with tag $tag")
        val split = xmlText.split(tag)
        val second = split[if (split.size > 1) 1 else 0]
        return second
            .substring(1, second.length - 2)
            .also { logger.debug("X12 extracted: $this") }
    }
}
