package com.saludaunclic.semefa.common.throwing

import com.ibm.mq.MQException
import com.saludaunclic.semefa.common.model.MqMessage
import com.saludaunclic.semefa.common.model.SemefaMessage
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

    @ExceptionHandler(value = [ServiceException::class, MqMaxAttemptReachedException::class])
    fun handleServiceException(exception: ServiceException): ResponseEntity<SemefaMessage> =
        with(exception) {
            logger.error("Found exception", this)
            ResponseEntity<SemefaMessage>(toMessage(exception), status)
        }

    @ExceptionHandler(MQException::class)
    fun handleServiceException(exception: MQException): ResponseEntity<MqMessage> =
        with(exception) {
            logger.error("Found exception", this)
            ResponseEntity<MqMessage>(toMessage(exception), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: java.lang.Exception): ResponseEntity<SemefaMessage> =
        with(exception) {
            logger.error("Found an exception $message", this)
            ResponseEntity<SemefaMessage>(toMessage(exception), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    private fun toMessage(exception: Exception): SemefaMessage =
        SemefaMessage(exception.message ?: UNKNOWN_ERROR_MESSAGE)

    private fun toMessage(exception: MQException): MqMessage =
        with(exception) {
            MqMessage(message ?: UNKNOWN_ERROR_MESSAGE, completionCode, reasonCode)
        }
}
