package com.saludaunclic.semefa.regafi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories

@SpringBootApplication
@EnableCaching
@ComponentScan(value = [ "com.saludaunclic.semefa.common", "com.saludaunclic.semefa.regafi" ])
@EnableJdbcRepositories(basePackages = [
    "com.saludaunclic.semefa.common.repository",
    "com.saludaunclic.semefa.regafi.repository"
])
class RegafiProxyApp

fun main(args: Array<String>) {
    runApplication<RegafiProxyApp>(*args)
}
