package com.saludaunclic.semefa.common.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MqConfig {
    @Bean
    @ConfigurationProperties(prefix = "mq")
    fun mqProperties() = MqProperties()
}