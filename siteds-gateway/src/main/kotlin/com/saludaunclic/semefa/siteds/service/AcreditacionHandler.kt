package com.saludaunclic.semefa.siteds.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.ibm.mq.MQException
import com.saludaunclic.semefa.common.service.DateService
import com.saludaunclic.semefa.common.service.MqClientService
import com.saludaunclic.semefa.common.throwing.MqMaxAttemptReachedException
import com.saludaunclic.semefa.common.throwing.ServiceException
import com.saludaunclic.semefa.common.util.SemefaUtils
import com.saludaunclic.semefa.siteds.model.MqAckResponse
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InLogAcreInsert271
import pe.gob.susalud.jr.transaccion.susalud.service.LogAcreInsert271Service
import java.util.UUID

@Service
class AcreditacionHandler(private val logAcreInsert271Service: LogAcreInsert271Service,
                          private val mqClientService: MqClientService,
                          private val dates: DateService,
                          private val objectMapper: ObjectMapper) {
    companion object {
        const val TX_NAME: String = "271_LOGACRE_INSERT"
    }

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun processAccreditation(inLogAcreInsert271: InLogAcreInsert271): MqAckResponse {
        val x12 = prepareX12(inLogAcreInsert271)
        return try {
            val message = putAndGetMessage(x12)
            mqResponse(message)
        } catch (ex: MqMaxAttemptReachedException) {
            logger.error(
                "Error enviando acreditación a MQ (con messageId [${StringUtils.defaultString(ex.messageId)}]): se llegó al límite intentos",
                ex)
            errorMqResponse(ex.message ?: StringUtils.EMPTY)
        } catch (ex: Exception) {
            logger.error("Error enviando acreditación a MQ: ${ex.message}", ex)
            errorMqResponse(ex.message ?: StringUtils.EMPTY)
        }
    }

    private fun putAndGetMessage(dataFrame: String): Map<String, String> =
        with(dataFrame) {
            logger.info("=== Start MQ Connection ===")
            logger.debug("Sending X12 message with this data: $this")

            try {
                mqClientService.sendMessageSync(this)
            } catch (ex: MQException) {
                throw ex
            } catch (ex: Exception) {
                throw ServiceException("Error en comunicación con MQ", ex, HttpStatus.SERVICE_UNAVAILABLE)
            }
        }

    private fun normalize(inLogAcreInsert271: InLogAcreInsert271): InLogAcreInsert271 =
        inLogAcreInsert271.apply {
            if (idCorrelativo == null) {
                idCorrelativo = UUID.randomUUID().toString()
            }
            val now = dates.now()
            feTransaccion = dates.formatDate(now)
            hoTransaccion = dates.formatTime(now)
        }

    private fun prepareX12(inLogAcreInsert271: InLogAcreInsert271): String =
        with(normalize(inLogAcreInsert271)) {
            if (logger.isDebugEnabled) {
                logger.debug("From bean to X12, bean: \n${objectMapper.writeValueAsString(this)}")
            }
            parseRequest(this).also {
                logger.debug("From bean to X12, X12: \n$it")
            }
        }

    private fun parseRequest(inLogAcreInsert271: InLogAcreInsert271): String =
        "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<RegistroAutRequest" +
            " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"" +
            " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"" +
            " xmlns=\"http://www.susalud.gob.pe/acreditacion/RegistroAutRequest.xsd\">" +
            "<txNombre>$TX_NAME</txNombre>" +
            "<codRemitente>${inLogAcreInsert271.idRemitente}</codRemitente>" +
            "<txPeticion>${logAcreInsert271Service.beanToX12N(inLogAcreInsert271)}</txPeticion>" +
            "</RegistroAutRequest>"

    private fun mqResponse(messageMap: Map<String, String>): MqAckResponse  =
        with(messageMap) {
            val messageId = this[MqClientService.MESSAGE_ID_KEY] ?: StringUtils.EMPTY
            val message = this[MqClientService.MESSAGE_KEY] ?: StringUtils.EMPTY
            val x12 = SemefaUtils.extractElement(logger, message, SemefaUtils.TX_RESPONSE_ELEMENT)
            val errorCode = SemefaUtils.extractElement(logger, message, SemefaUtils.NO_ERROR)
            logger.debug("From X12 to bean, X12:${System.lineSeparator()}$x12")
            MqAckResponse(messageId, message, errorCode, x12)
        }

    private fun errorMqResponse(message: String) = MqAckResponse(
        StringUtils.EMPTY,
        message,
        StringUtils.EMPTY,
        StringUtils.EMPTY)
}
