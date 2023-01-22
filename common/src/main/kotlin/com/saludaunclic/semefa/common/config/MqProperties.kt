package com.saludaunclic.semefa.common.config

import org.apache.commons.lang3.StringUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "mq")
class MqProperties(var queueManager: String = StringUtils.EMPTY,
                   var queueIn: String = StringUtils.EMPTY,
                   var queueOut: String = StringUtils.EMPTY,
                   var channel: String = StringUtils.EMPTY,
                   var hostname: String = StringUtils.EMPTY,
                   var port: Int = 0,
                   var numberOfTries: Int = 0,
                   var waitAfterTry: Long = 0,
                   var waitInterval: Int = 0)
