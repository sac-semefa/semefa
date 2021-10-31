package com.saludaunclic.semefa.common.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "date")
class DateProperties {
    lateinit var timeZone: String
}