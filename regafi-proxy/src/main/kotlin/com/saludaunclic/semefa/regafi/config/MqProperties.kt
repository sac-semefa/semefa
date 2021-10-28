package com.saludaunclic.semefa.regafi.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "mq")
class MqProperties {
    lateinit var queueManager: String
    lateinit var queueIn: String
    lateinit var queueOut: String
    lateinit var channel: String
    lateinit var hostname: String
    var port: Int = 0
}