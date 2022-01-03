package com.saludaunclic.semefa.common.model

data class MqMessage(
    var message: String? = null,
    var completionCode: Int? = 0,
    var reasonCode: Int? = 0
)
