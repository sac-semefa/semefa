package com.saludaunclic.semefa.siteds.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "cxf")
class CxfProperties(var path: String = "")