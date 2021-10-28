package com.saludaunclic.semefa.siteds.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "siteds")
class SitedsProperties {
    lateinit var iafaCode: String
}
