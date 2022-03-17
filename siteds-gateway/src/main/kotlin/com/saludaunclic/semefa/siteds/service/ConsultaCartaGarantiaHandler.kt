package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.ResponseIn278ResCG
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.service.In278ResCGService
import pe.gob.susalud.jr.transaccion.susalud.service.In278SolCGService
import pe.gob.susalud.ws.siteds.schemas.GetConsultaxCartaGarantiaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaxCartaGarantiaResponse

@Service
class ConsultaCartaGarantiaHandler(private val sitedsValidator: SitedsValidator,
                                   private val in278SolCGService: In278SolCGService,
                                   private val in278ResCGService: In278ResCGService,
                                   private val handlerProvider: HandlerProvider
): StringOutputSitedsHandler<GetConsultaxCartaGarantiaRequest, GetConsultaxCartaGarantiaResponse>() {
    override fun handleRequest(request: GetConsultaxCartaGarantiaRequest): String {
        sitedsValidator.validate(request)

        val in278SolCG = in278SolCGService.x12NToBean(request.txPeticion)
        val bean = sendBean(handlerProvider.resolvePath(this), in278SolCG, ResponseIn278ResCG::class.java)

        return in278ResCGService.beanToX12N(bean.data)
    }

    override fun createResponse(errorCode: String, output: String): GetConsultaxCartaGarantiaResponse =
        GetConsultaxCartaGarantiaResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_278_RES_CG
            txRespuesta = output
        }
}
