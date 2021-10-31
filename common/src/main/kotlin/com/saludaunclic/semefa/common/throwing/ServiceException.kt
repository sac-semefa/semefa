package com.saludaunclic.semefa.common.throwing

import org.springframework.http.HttpStatus

class ServiceException: RuntimeException {
    val status: HttpStatus

    constructor(message: String, status: HttpStatus): super(message) {
        this.status = status
    }

    constructor(message: String, cause: Throwable, status: HttpStatus): super(message, cause) {
        this.status = status
    }
}
