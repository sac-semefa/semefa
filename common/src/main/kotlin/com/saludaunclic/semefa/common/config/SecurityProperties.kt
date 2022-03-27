package com.saludaunclic.semefa.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "web.security")
class SecurityProperties(var publicUrls: List<String> = listOf(),
                         var protectedUrls: List<String> = listOf())