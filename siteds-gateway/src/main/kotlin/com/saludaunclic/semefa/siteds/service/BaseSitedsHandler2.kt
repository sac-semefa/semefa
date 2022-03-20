package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.ErrorCodes
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.throwing.SitedsException
import com.saludaunclic.semefa.siteds.util.LoggingUtils
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.web.client.RestTemplate
import java.util.Collections

abstract class BaseSitedsHandler2<in Req: Any, out Res: Any, In: Any, Out: Any>: SitedsHandler2<Req, Res, In, Out> {
    @Autowired private lateinit var restTemplate: RestTemplate
    @Autowired protected lateinit var sitedsProperties: SitedsProperties
    @Autowired protected lateinit var sitedsValidator: SitedsValidator
    @Autowired protected lateinit var handlerProvider: HandlerProvider

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    protected abstract fun validate(request: Req)
    protected abstract fun extractInput(request: Req): In
    protected abstract fun <R : Response<Out>> resolveResponseClass(): Class<R>

    override fun handle(request: Req): Res =
        with(request) {
            try {
                validate(this)
                val input = extractInput(this)
                val output = sendBean(handlerProvider.resolvePath(this), input, resolveResponseClass())
                createResponse(output)
            } catch (ex: SitedsException) {
                logger.error("Error when handling request of class ${this::class.java.name}: ${ex.message}", ex)
                createErrorResponse(ex.errorCode, this)
            } catch (ex: Exception) {
                logger.error(
                    "Unexpected error when handling request of class ${this::class.java.name}: ${ex.message}",
                    ex)
                createErrorResponse(ErrorCodes.SYSTEM_ERROR, this)
            }
        }

    fun <R: Response<Out>> sendBean(url: String, input: In, clazz: Class<R>): Out =
        with(input) {
            LoggingUtils.logSend(logger, input)

            val entity: HttpEntity<In> = HttpEntity(input, headers())
            val responseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                clazz)
            val response = responseEntity.body
                ?: throw SitedsException(
                    "Error posting bean of class ${input::class.java.name}",
                    ErrorCodes.SYSTEM_ERROR)

            LoggingUtils.logReceive(logger, response)

            response.data ?: throw SitedsException("Error getting data from ${clazz.name}")
        }

    private fun headers(): HttpHeaders = HttpHeaders()
        .apply {
            this.accept = Collections.singletonList(MediaType.ALL);
            this.contentType = MediaType.APPLICATION_JSON
            this.set(HttpHeaders.AUTHORIZATION, sitedsProperties.apiKey)
        }
}
