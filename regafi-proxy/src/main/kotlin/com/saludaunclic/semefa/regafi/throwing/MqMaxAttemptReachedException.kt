package com.saludaunclic.semefa.regafi.throwing

class MqMaxAttemptReachedException(val messageId: String, message: String): Exception(message)