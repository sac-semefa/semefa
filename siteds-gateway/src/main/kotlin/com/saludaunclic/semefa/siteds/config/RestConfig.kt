package com.saludaunclic.semefa.siteds.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate


@Configuration
class RestConfig {
    @Bean
    @Primary
    fun objectMapper(): ObjectMapper = jacksonObjectMapper()
        .setSerializationInclusion(JsonInclude.Include.NON_NULL)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    @Bean
    fun restTemplate(@Autowired objectMapper: ObjectMapper): RestTemplate = RestTemplate().apply {
        this.messageConverters = mutableListOf<HttpMessageConverter<*>>(
            MappingJackson2HttpMessageConverter().apply {
                this.objectMapper = objectMapper
            }
        )
    }
}
