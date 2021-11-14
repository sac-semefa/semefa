package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.ErrorCodes
import com.saludaunclic.semefa.siteds.throwing.SitedsException
import com.saludaunclic.semefa.siteds.util.LoggingUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
abstract class BaseSitedsHandler<in Req: Any, out Res: Any>: SitedsHandler<Req, Res> {
    val logger: Logger = LoggerFactory.getLogger(javaClass)

    fun handle(request: Req): Res =
        with(request) {
            val resolvedTxName: String = resolveTxName(this)
            try {
                handleRequest(this)
                createResponse(ErrorCodes.NO_ERROR, resolvedTxName)
            } catch (ex: SitedsException) {
                logger.error("Error when handling request of class ${this::class.java.name}: ${ex.message}", ex)
                createResponse(ex.errorCode, resolvedTxName)
            } catch (ex: Exception) {
                logger.error(
                    "Unexpected error when handling request of class ${this::class.java.name}: ${ex.message}",
                    ex)
                createResponse(ErrorCodes.SYSTEM_ERROR, resolvedTxName)
            }
        }

    fun <Rq: Any, Rs: Any> sendBean(url: String, request: Rq, clazz: Class<Rs>): Rs {
        LoggingUtils.logSend(logger, request)
        val response: Rs = buildWebClient(url)
            .post()
            .bodyValue(request)
            .retrieve()
            .toEntity(clazz)
            .block()
            ?.body ?: throw SitedsException(
                "Error posting bean of class ${request::class.java.name}",
                ErrorCodes.SYSTEM_ERROR)
        LoggingUtils.logReceive(logger, response)
        return response
    }

    abstract fun resolveTxName(request: Req): String

    private fun buildWebClient(url: String) = WebClient
        .builder()
        .baseUrl(url)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .build()
}
