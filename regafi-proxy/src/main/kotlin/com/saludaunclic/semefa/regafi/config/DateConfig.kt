package com.saludaunclic.semefa.regafi.config

import com.saludaunclic.semefa.regafi.service.date.DateService
import com.saludaunclic.semefa.regafi.service.date.DefaultDateService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.TimeZone

@Configuration
class DateConfig(val dateProperties: DateProperties) {
    @Bean fun dateService(): DateService = DefaultDateService(TimeZone.getTimeZone(dateProperties.timeZone))
}
