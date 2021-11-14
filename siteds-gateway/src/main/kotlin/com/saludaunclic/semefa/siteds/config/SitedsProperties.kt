package com.saludaunclic.semefa.siteds.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "siteds")
class SitedsProperties(var sacUrl: String = "",
                       var iafaCode: String = "",
                       var susaludUser: String = "",
                       var susaludPassword: String = "")
