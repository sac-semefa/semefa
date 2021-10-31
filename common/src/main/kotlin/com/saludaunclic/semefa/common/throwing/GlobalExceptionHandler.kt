package com.saludaunclic.semefa.common.throwing

import com.saludaunclic.semefa.common.model.RestMessage
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
    fun handleServiceException(exception: ServiceException): ResponseEntity<RestMessage> =
        with(exception) {
            logger.error("Found exception", this)
            return ResponseEntity<RestMessage>(toMessage(exception), status)
        }

    @ExceptionHandler(Exception::class)
    fun handleException(exception: java.lang.Exception): ResponseEntity<RestMessage> =
        with(exception) {
            logger.error("Found an exception $message", this)
            ResponseEntity<RestMessage>(toMessage(exception), HttpStatus.INTERNAL_SERVER_ERROR)
        }

    private fun toMessage(exception: Exception): RestMessage = RestMessage(exception.message ?: UNKNOWN_ERROR_MESSAGE)
}
