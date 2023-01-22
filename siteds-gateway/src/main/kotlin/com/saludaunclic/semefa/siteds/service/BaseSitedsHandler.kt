package com.saludaunclic.semefa.siteds.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.saludaunclic.semefa.siteds.SitedsConstants.ErrorCodes
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.throwing.SitedsException
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import java.util.Collections

abstract class BaseSitedsHandler<in Req: Any, out Res: Any, In: Any, Out: Any>: SitedsHandler<Req, Res, In, Out> {
    @Autowired private lateinit var restTemplate: RestTemplate
    @Autowired private lateinit var objectMapper: ObjectMapper
    @Autowired private lateinit var sitedsProperties: SitedsProperties
    @Autowired private lateinit var handlerProvider: HandlerProvider
    @Autowired protected lateinit var sitedsValidator: SitedsValidator
    @Autowired protected lateinit var loggingHelper: LoggingHelper

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    protected abstract fun validate(request: Req)
    protected abstract fun extractInput(request: Req): In
    protected abstract fun <R : Response<Out>> resolveResponseClass(): Class<R>

    override fun handle(request: Req): Res =
        try {
            loggingHelper.logReceiveXml(logger, request)

            validate(request)
            val input = extractInput(request)
            val url = handlerProvider.resolvePath(this)
            val output = sendBean(url, input, resolveResponseClass())
            val response = createResponse(output)

            loggingHelper.logSendXml(logger, response)
            response
        } catch (ex: SitedsException) {
            logger.error("Error when handling request of class ${this::class.java.name}: ${ex.message}", ex)
            createErrorResponse(ex.errorCode, request)
        } catch (ex: Exception) {
            logger.error(
                "Unexpected error when handling request of class ${this::class.java.name}: ${ex.message}",
                ex)
            createErrorResponse(ErrorCodes.SYSTEM_ERROR, request)
        }

    private fun <R: Response<Out>> sendBean(url: String, input: In, clazz: Class<R>): Out =
        with(input) {
            loggingHelper.logSendJson(logger, input, url)

            val request: HttpEntity<In> = HttpEntity(input, headers())
            val entity = restTemplate.postForEntity(url, request, clazz)
            val response = entity.body
                ?: throw SitedsException(
                    "Error posting bean of class ${input::class.java.name}",
                    ErrorCodes.SYSTEM_ERROR)

            loggingHelper.logReceiveJson(logger, response, url)

            response.data ?: throw SitedsException("Error getting data from ${clazz.name}")
        }

    private fun headers(): HttpHeaders = HttpHeaders()
        .apply {
            this.accept = Collections.singletonList(MediaType.APPLICATION_JSON);
            this.contentType = MediaType.APPLICATION_JSON
            this.set(HttpHeaders.AUTHORIZATION, sitedsProperties.apiKey)
        }
}
