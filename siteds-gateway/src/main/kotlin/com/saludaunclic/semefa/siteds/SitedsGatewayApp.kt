package com.saludaunclic.semefa.siteds

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@SpringBootApplication
@ConfigurationPropertiesScan(value = [
    "com.saludaunclic.semefa.common.config",
    "com.saludaunclic.semefa.siteds.config"
])
@EnableJdbcRepositories(basePackages = [ "com.saludaunclic.semefa.common.repository" ])
@EnableCaching
class SitedsGatewayApp

fun main(args: Array<String>) {
    runApplication<SitedsGatewayApp>(*args)
}
