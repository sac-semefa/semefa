package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In278ResCG
import pe.gob.susalud.jr.transaccion.susalud.bean.In278SolCG
import pe.gob.susalud.jr.transaccion.susalud.bean.In997ResAut
import pe.gob.susalud.jr.transaccion.susalud.service.In278ResCGService
import pe.gob.susalud.jr.transaccion.susalud.service.In278SolCGService
import pe.gob.susalud.ws.siteds.schemas.GetConsultaxCartaGarantiaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaxCartaGarantiaResponse

@Service
class ConsultaCartaGarantiaHandler(private val sitedsValidator: SitedsValidator,
                                   private val sitedsProperties: SitedsProperties,
                                   private val in278SolCGService: In278SolCGService,
                                   private val in278ResCGService: In278ResCGService
): BaseSitedsHandler<GetConsultaxCartaGarantiaRequest, GetConsultaxCartaGarantiaResponse>() {
    companion object {
        const val PATH: String = "/concartaga"
    }

    override fun handleRequest(request: GetConsultaxCartaGarantiaRequest) {
        sitedsValidator.validate(request)

        val in278SolCG: In278SolCG = in278SolCGService.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, in278SolCG)

        val in278ResCG: In278ResCG = sendBean(
            sitedsProperties.sacUrl + PATH,
            in278SolCG,
            In278ResCG::class.java)
        val x12: String = in278ResCGService.beanToX12N(in278ResCG)
        logConvertResponse(logger, in278ResCG, x12)
    }

    override fun createResponse(errorCode: String): GetConsultaxCartaGarantiaResponse =
        GetConsultaxCartaGarantiaResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_278_RES_CG
        }
}
