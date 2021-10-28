package com.saludaunclic.semefa.regafi.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "date")
class DateProperties {
    lateinit var timeZone: String
}