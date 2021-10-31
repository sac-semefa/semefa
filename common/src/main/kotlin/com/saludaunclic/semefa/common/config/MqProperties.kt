package com.saludaunclic.semefa.common.config

class MqProperties {
    lateinit var queueManager: String
    lateinit var queueIn: String
    lateinit var queueOut: String
    lateinit var channel: String
    lateinit var hostname: String
    var port: Int = 0
}
