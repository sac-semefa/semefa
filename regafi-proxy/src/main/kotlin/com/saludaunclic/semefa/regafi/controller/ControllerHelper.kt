package com.saludaunclic.semefa.regafi.controller

import com.saludaunclic.semefa.common.throwing.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import javax.servlet.http.HttpServletRequest

class ControllerHelper {
    companion object {
        val LOCAL_LOOPBACK_OPTIONS: Set<String> = setOf("localhost", "127.0.0.1", "0:0:0:0:0:0:0:1")

        fun <E> whenFromLocalhost(request: HttpServletRequest, supplier: () -> ResponseEntity<E>): ResponseEntity<E> =
            if (LOCAL_LOOPBACK_OPTIONS.contains(request.remoteAddr)) supplier()
            else throw ServiceException(
                    "Por seguridad y para inicializar aplicación necesita acceder desde localhost",
                    HttpStatus.FORBIDDEN)
    }
}
