package com.saludaunclic.semefa.siteds

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@SpringBootApplication(scanBasePackages = [ "com.saludaunclic.semefa.common" ])
@EnableJdbcRepositories(basePackages = [ "com.saludaunclic.semefa.common.repository" ])
@EnableCaching
class SitedsGatewayApp

fun main(args: Array<String>) {
    runApplication<SitedsGatewayApp>(*args)
}
