package com.saludaunclic.semefa.regafi.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
class TokenProperties {
    lateinit var issuer: String
    var expirationSec: Long = 0
    var clockSkewSec: Long = 0
    lateinit var secret: String
}
