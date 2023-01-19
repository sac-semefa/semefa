package com.saludaunclic.semefa.siteds.model

class MqAckResponse(val messageId: String,
                    val message: String,
                    val errorCode: String,
                    val x12: String)