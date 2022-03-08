package com.saludaunclic.semefa.regafi.throwing

import com.ibm.mq.MQException
import com.saludaunclic.semefa.common.model.MqMessage
import com.saludaunclic.semefa.common.throwing.ServiceException
import com.saludaunclic.semefa.regafi.model.RegafiMessage
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {
    companion object {
        const val UNKNOWN_ERROR_MESSAGE: String = "Unknown server error occurred"
    }

    @ExceptionHandler(ServiceException::class)
    fun handleServiceException(exception: ServiceException): ResponseEntity<RegafiMessage> =
        with(exception) {
            logger.error("Found exception", this)
            return ResponseEntity<RegafiMessage>(toMessage(exception), status)
        }

    @ExceptionHandler(MQException::class)
    fun handleServiceException(exception: MQException): ResponseEntity<MqMessage> =
        with(exception) {
            logger.error("Found exception", this)
            return ResponseEntity<MqMessage>(toMessage(exception), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: java.lang.Exception): ResponseEntity<RegafiMessage> =
        with(exception) {
            logger.error("Found an exception $message", this)
            ResponseEntity<RegafiMessage>(toMessage(exception), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    private fun toMessage(exception: Exception): RegafiMessage =
        RegafiMessage(exception.message ?: UNKNOWN_ERROR_MESSAGE)

    private fun toMessage(exception: MQException): MqMessage =
        with(exception) {
            MqMessage(message ?: UNKNOWN_ERROR_MESSAGE, completionCode, reasonCode)
        }
}
