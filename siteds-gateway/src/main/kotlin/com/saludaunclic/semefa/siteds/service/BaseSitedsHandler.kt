package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.ErrorCodes
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.throwing.SitedsException
import com.saludaunclic.semefa.siteds.util.LoggingUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import java.util.Collections

abstract class BaseSitedsHandler<in Req: Any, out Res: Any, Out: Any>: SitedsHandler<Req, Res, Out> {
    @Autowired private lateinit var restTemplate: RestTemplate
    @Autowired protected lateinit var sitedsProperties: SitedsProperties
    protected val logger: Logger = LoggerFactory.getLogger(javaClass)


    fun handle(request: Req): Res =
        with(request) {
            try {
                val output = handleRequest(this)
                createResponse(ErrorCodes.NO_ERROR, output)
            } catch (ex: SitedsException) {
                logger.error("Error when handling request of class ${this::class.java.name}: ${ex.message}", ex)
                createErrorResponse(ex.errorCode)
            } catch (ex: Exception) {
                logger.error(
                    "Unexpected error when handling request of class ${this::class.java.name}: ${ex.message}",
                    ex)
                createErrorResponse(ErrorCodes.SYSTEM_ERROR)
            }
        }

    fun <Rq: Any, T: Any, Rs: Response<T>> sendBean(url: String, request: Rq, clazz: Class<Rs>): Rs {
        LoggingUtils.logSend(logger, request)

        val entity: HttpEntity<Rq> = HttpEntity(request, headers())
        val responseEntity: ResponseEntity<Rs> = restTemplate.exchange(url, HttpMethod.POST, entity, clazz)
        val response = responseEntity.body ?: throw SitedsException(
                "Error posting bean of class ${request::class.java.name}",
                ErrorCodes.SYSTEM_ERROR)

        LoggingUtils.logReceive(logger, response)

        return response
    }

    protected abstract fun errorOutput(): Out

    private fun createErrorResponse(errorCode: String): Res = createResponse(errorCode, errorOutput())

    private fun headers(): HttpHeaders = HttpHeaders()
        .apply {
            this.accept = Collections.singletonList(MediaType.ALL);
            this.contentType = MediaType.APPLICATION_JSON
            this.set(HttpHeaders.AUTHORIZATION, sitedsProperties.apiKey)
        }
}
