package com.saludaunclic.semefa.siteds.throwing

class MqMaxAttemptReachedException(val messageId: String, message: String): Exception(message)