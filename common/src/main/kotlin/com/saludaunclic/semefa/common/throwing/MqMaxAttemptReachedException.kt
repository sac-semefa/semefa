package com.saludaunclic.semefa.common.throwing

class MqMaxAttemptReachedException(val messageId: String, message: String): Exception(message)