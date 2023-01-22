package com.saludaunclic.semefa.siteds.config

import org.apache.commons.lang3.StringUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "siteds")
class SitedsProperties(var sacUrl: String = StringUtils.EMPTY,
                       var apiKey: String = StringUtils.EMPTY,
                       var devMode: String = "false") {
    fun isDevMode() = "true" == devMode.lowercase().trim()
}
