package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.ResponseInConMed271
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConMed271Service
import pe.gob.susalud.ws.siteds.schemas.GetCondicionMedicaRequest
import pe.gob.susalud.ws.siteds.schemas.GetCondicionMedicaResponse

@Service
class CondicionMedicaHandler(private val sitedsValidator: SitedsValidator,
                             private val conAse270Service: ConAse270Service,
                             private val conMed271Service: ConMed271Service,
                             private val handlerProvider: HandlerProvider
): StringOutputSitedsHandler<GetCondicionMedicaRequest, GetCondicionMedicaResponse>() {
    override fun handleRequest(request: GetCondicionMedicaRequest): String {
        sitedsValidator.validate(request)

        val inConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        val bean = sendBean(handlerProvider.resolvePath(this), inConAse270, ResponseInConMed271::class.java)

        return conMed271Service.beanToX12N(bean.data)
    }

    override fun createResponse(errorCode: String, output: String): GetCondicionMedicaResponse =
        GetCondicionMedicaResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_CON_MED
            txRespuesta = output
        }
}
