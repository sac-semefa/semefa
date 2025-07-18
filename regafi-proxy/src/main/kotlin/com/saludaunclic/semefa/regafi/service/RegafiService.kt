package com.saludaunclic.semefa.regafi.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.ibm.mq.MQException
import com.saludaunclic.semefa.common.service.DateService
import com.saludaunclic.semefa.common.service.MqClientService
import com.saludaunclic.semefa.common.throwing.MqMaxAttemptReachedException
import com.saludaunclic.semefa.common.throwing.ServiceException
import com.saludaunclic.semefa.common.util.SemefaUtils
import com.saludaunclic.semefa.regafi.dto.SacIn997RegafiUpdate
import com.saludaunclic.semefa.regafi.mapper.RegafyMapper
import com.saludaunclic.semefa.regafi.model.DataFrame
import com.saludaunclic.semefa.regafi.model.DataFrameStatus
import com.saludaunclic.semefa.regafi.model.DataFrameType
import com.saludaunclic.semefa.regafi.repository.DataFrameRepository
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In271RegafiUpdate
import pe.gob.susalud.jr.transaccion.susalud.service2.RegafiUpdate271Service
import pe.gob.susalud.jr.transaccion.susalud.service2.RegafiUpdate997Service
import java.lang.System.lineSeparator
import java.util.UUID

@Service
class RegafiService(
    private val regafiUpdate271Service: RegafiUpdate271Service,
    private val regafiUpdate997Service: RegafiUpdate997Service,
    private val regafiMapper: RegafyMapper,
    private val mqClientService: MqClientService,
    private val errorsService: ErrorsService,
    private val dataFrameRepository: DataFrameRepository,
    private val dates: DateService,
    private val objectMapper: ObjectMapper
) {
    companion object {
        const val TX_NAME: String = "271_REGAFI_UPDATE"
    }

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun process271DataFrame(request: In271RegafiUpdate): SacIn997RegafiUpdate {
        val x12 = prepareX12(request)
        if (request.coError != null && request.coError != SemefaUtils.NO_ERROR) {
            return handleRequestError(request)
        }

        return try {
            val mqResult = putAndGetMessage(x12)
            val update271 = processResponse(mqResult)
            update271.also {
                if (it.idMensaje != null) {
                    persistDataFrame(
                        createDataFrame(
                            it.idMensaje,
                            DataFrameStatus.PROCESSED
                        ).apply { correlativeId = it.idCorrelativo })
                } else {
                    logger.warn("Not persisting null messageId for message: ${mqResult[MqClientService.MESSAGE_KEY]}")
                }
            }
        } catch (ex: MqMaxAttemptReachedException) {
            fallback997(ex, persistDataFrame(createDataFrame(ex.messageId, DataFrameStatus.PENDING)))
        }
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

    private fun handleRequestError(request: In271RegafiUpdate): SacIn997RegafiUpdate {
        val fieldErrorId = request.coError.substring(0, 3).toInt()
        val ruleErrorId = request.coError.substring(3).toInt()
        val fieldError = errorsService.getFieldError(fieldErrorId) ?: "Unknown"
        val ruleError = errorsService.getFieldErrorRule(ruleErrorId) ?: "Unknown"
        val errorMessage = "$fieldError -> $ruleError"
        logger.error("Found In271RegafiUpdate validation error: $errorMessage")
        return SacIn997RegafiUpdate()
            .apply {
                idError = request.coError
                mensajeError = errorMessage
            }
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

    private fun prepareX12(in271RegafiUpdate: In271RegafiUpdate): String =
        with(normalize(in271RegafiUpdate)) {
            if (logger.isDebugEnabled) {
                logger.debug("From bean to X12, bean: \n${objectMapper.writeValueAsString(this)}")
            }
            parseRequest(this).also {
                logger.debug("From bean to X12, X12: \n$it")
            }
        }

    private fun normalize(in271RegafiUpdate: In271RegafiUpdate): In271RegafiUpdate =
        in271RegafiUpdate.apply {
            if (idCorrelativo == null) {
                idCorrelativo = UUID.randomUUID().toString()
            }
            val now = dates.now()
            feTransaccion = dates.formatDate(now)
            hoTransaccion = dates.formatTime(now)
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

    private fun putAndGetMessage(dataFrame: String): Map<String, String> =
        with(dataFrame) {
            logger.info("=== Start MQ Connection ===")
            logger.info("Sending X12 message with this data: $this")

            try {
                mqClientService.sendMessageSync(this)
            } catch (ex: Exception) {
                throw when(ex) {
                    is MQException,
                    is MqMaxAttemptReachedException -> ex
                    else -> ServiceException("Error en comunicación con MQ", ex, HttpStatus.SERVICE_UNAVAILABLE)
                }
            }
        }

    private fun handleResponseError(errorCode: String): SacIn997RegafiUpdate {
        val serviceError = errorsService.getServiceError(errorCode.toInt())
        logger.error("Found SacIn997RegafiUpdate service error: $serviceError")
        return SacIn997RegafiUpdate()
            .apply {
                idError = errorCode
                mensajeError = serviceError
            }
    }

    private fun processResponse(messageMap: Map<String, String>): SacIn997RegafiUpdate =
        with(messageMap) {
            val messageId = this[MqClientService.MESSAGE_ID_KEY] ?: StringUtils.EMPTY
            val message = this[MqClientService.MESSAGE_KEY] ?:  StringUtils.EMPTY
            val x12 = SemefaUtils.extractElement(logger, message, SemefaUtils.TX_RESPONSE_ELEMENT)
            val errorCode = SemefaUtils.extractElement(logger, message, SemefaUtils.ERROR_CODE_ELEMENT)
            if (errorCode != SemefaUtils.NO_ERROR) {
                return handleResponseError(errorCode)
            }

            logger.debug("From X12 to bean, X12:${lineSeparator()}$x12")
            return regafiMapper.toSac997Update(
                messageId,
                regafiUpdate997Service.x12NToBean(x12).apply { isFlag = true }
            ).apply {
                mensajeError
                in271RegafiUpdateExcepcion.forEach {
                    if (it.coCampoErr.isNotBlank()) {
                        it.errorCampo = errorsService.getFieldError(it.coCampoErr.toInt())
                    }
                    if (it.inCoErrorEncontrado.isNotBlank()) {
                        it.errorCampoRegla = errorsService.getFieldErrorRule(it.inCoErrorEncontrado.toInt())
                    }
                }
                if (logger.isDebugEnabled) {
                    logger.debug("From X12 to bean, bean:${lineSeparator()}${objectMapper.writeValueAsString(this)}")
                }
            }
        }
}
