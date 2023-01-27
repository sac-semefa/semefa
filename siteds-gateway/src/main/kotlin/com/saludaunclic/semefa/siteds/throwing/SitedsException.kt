package com.saludaunclic.semefa.siteds.throwing

class SitedsException: RuntimeException {
    val errorCode: String

    constructor(message: String, errorCode: String): super(message) {
        this.errorCode = errorCode
    }

    constructor(errorCode: String) : super() {
        this.errorCode = errorCode
    }
}