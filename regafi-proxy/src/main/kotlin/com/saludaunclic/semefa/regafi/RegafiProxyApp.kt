package com.saludaunclic.semefa.regafi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@ConfigurationPropertiesScan(value = [
    "com.saludaunclic.semefa.common.config",
    "com.saludaunclic.semefa.regafi.config"
])
@EnableCaching
class RegafiProxyApp

fun main(args: Array<String>) {
    runApplication<RegafiProxyApp>(*args)
}
