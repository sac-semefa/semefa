package com.saludaunclic.semefa.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "jwt")
class TokenProperties(var issuer: String = "",
                      var expirationSec: Long = 0,
                      var clockSkewSec: Long = 0,
                      var secret: String = "")

