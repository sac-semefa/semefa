package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.ResponseIn997ResAut
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.service.In997ResAutService
import pe.gob.susalud.jr.transaccion.susalud.service.SolAut271Service
import pe.gob.susalud.ws.siteds.schemas.GetNumAutorizacionRequest
import pe.gob.susalud.ws.siteds.schemas.GetNumAutorizacionResponse

@Service
class NumAutorizacionHandler(private val sitedsValidator: SitedsValidator,
                             private val solAut271Service: SolAut271Service,
                             private val in997ResAutService: In997ResAutService,
                             private val handlerProvider: HandlerProvider
): StringOutputSitedsHandler<GetNumAutorizacionRequest, GetNumAutorizacionResponse>() {
    override fun handleRequest(request: GetNumAutorizacionRequest): String {
        sitedsValidator.validate(request)

        val inSolAut271 = solAut271Service.x12NToBean(request.txPeticion)
        val bean = sendBean(handlerProvider.resolvePath(this), inSolAut271, ResponseIn997ResAut::class.java)

        return in997ResAutService.beanToX12N(bean.data)
    }

    override fun createResponse(errorCode: String, output: String): GetNumAutorizacionResponse =
        GetNumAutorizacionResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_997_RES_AUT
            txRespuesta = output
        }
}
