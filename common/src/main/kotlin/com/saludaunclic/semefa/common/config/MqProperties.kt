package com.saludaunclic.semefa.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "mq")
class MqProperties(var queueManager: String = "",
                   var queueIn: String = "",
                   var queueOut: String = "",
                   var channel: String = "",
                   var hostname: String = "",
                   var port: Int = 0,
                   var numberOfTries: Int = 0,
                   var waitAfterTry: Long = 0,
                   var waitInterval: Int = 0)
