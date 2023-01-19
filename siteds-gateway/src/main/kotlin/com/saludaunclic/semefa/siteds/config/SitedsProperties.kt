package com.saludaunclic.semefa.siteds.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "siteds")
class SitedsProperties(var sacUrl: String = "",
                       var apiKey: String = "",
                       var devMode: Boolean = false)
