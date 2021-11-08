package com.saludaunclic.semefa.siteds.service

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaRequest

@Component
class SitedsHandlerProvider(private val consultaEntVinculadaHandler: GetConsultaEntVinculadaHandler,
                            private val noopHandler: NoopHandler) {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    /*fun <Req, H: SitedsHandler<Req, Any>> resolveProvider(request: Req): H =
        with(request) {
            logger.info("Resolving provider for request of class {}", request!!::class.java)
            return when(request) {
                is GetConsultaEntVinculadaRequest -> consultaEntVinculadaHandler
                else -> noopHandler
            }
        }*/
}