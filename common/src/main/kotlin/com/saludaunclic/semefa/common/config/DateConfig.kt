package com.saludaunclic.semefa.common.config

import com.saludaunclic.semefa.common.service.date.DateService
import com.saludaunclic.semefa.common.service.date.DefaultDateService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.TimeZone

@Configuration
class DateConfig {
    lateinit var dateProperties: DateProperties

    @Bean fun dateService(): DateService = DefaultDateService(TimeZone.getTimeZone(dateProperties.timeZone))
}
