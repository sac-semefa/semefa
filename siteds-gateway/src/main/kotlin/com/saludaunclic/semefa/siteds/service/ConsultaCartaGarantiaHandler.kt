package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In278ResCG
import pe.gob.susalud.jr.transaccion.susalud.service.In278ResCGService
import pe.gob.susalud.jr.transaccion.susalud.service.In278SolCGService
import pe.gob.susalud.ws.siteds.schemas.GetConsultaxCartaGarantiaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaxCartaGarantiaResponse

@Service
class ConsultaCartaGarantiaHandler(private val sitedsValidator: SitedsValidator,
                                   private val in278SolCGService: In278SolCGService,
                                   private val in278ResCGService: In278ResCGService,
                                   private val handlerProvider: HandlerProvider,
                                   sitedsProperties: SitedsProperties
): StringOutputSitedsHandler<GetConsultaxCartaGarantiaRequest, GetConsultaxCartaGarantiaResponse>(sitedsProperties) {
    override fun handleRequest(request: GetConsultaxCartaGarantiaRequest): String {
        sitedsValidator.validate(request)

        val in278SolCG = in278SolCGService.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, in278SolCG)

        val in278ResCG = sendBean(handlerProvider.resolvePath(this), in278SolCG, In278ResCG::class.java)
        val x12 = in278ResCGService.beanToX12N(in278ResCG)
        logConvertResponse(logger, in278ResCG, x12)

        return x12
    }

    override fun createResponse(errorCode: String, output: String): GetConsultaxCartaGarantiaResponse =
        GetConsultaxCartaGarantiaResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_278_RES_CG
            txRespuesta = output
        }
}
