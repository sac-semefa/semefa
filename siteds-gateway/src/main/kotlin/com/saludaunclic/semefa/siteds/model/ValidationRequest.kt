package com.saludaunclic.semefa.siteds.model

data class ValidationRequest(var transaction: String?,
                             var userCode: String?,
                             var exceptionCode: String?,
                             var senderCode: String?,
                             var txRequest: String?)
