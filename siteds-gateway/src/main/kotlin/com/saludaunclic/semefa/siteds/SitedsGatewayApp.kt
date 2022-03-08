package com.saludaunclic.semefa.siteds

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication(scanBasePackages = [ "com.saludaunclic.semefa.common", "com.saludaunclic.semefa.siteds" ])
@EnableCaching
class SitedsGatewayApp

fun main(args: Array<String>) {
    runApplication<SitedsGatewayApp>(*args)
}
