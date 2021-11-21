package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InConMed271
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConMed271Service
import pe.gob.susalud.ws.siteds.schemas.GetCondicionMedicaRequest
import pe.gob.susalud.ws.siteds.schemas.GetCondicionMedicaResponse

@Service
class CondicionMedicaHandler(private val sitedsValidator: SitedsValidator,
                             private val sitedsProperties: SitedsProperties,
                             private val conAse270Service: ConAse270Service,
                             private val conMed271Service: ConMed271Service
): StringOutputSitedsHandler<GetCondicionMedicaRequest, GetCondicionMedicaResponse>() {
    companion object {
        const val PATH: String = "/conmedica"
    }

    override fun handleRequest(request: GetCondicionMedicaRequest): String {
        sitedsValidator.validate(request)

        val inConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inConAse270)

        val bean = sendBean(sitedsProperties.sacUrl + PATH, inConAse270, InConMed271::class.java)
        val x12 = conMed271Service.beanToX12N(bean)
        logConvertResponse(logger, bean, x12)

        return x12
    }

    override fun createResponse(errorCode: String, output: String): GetCondicionMedicaResponse =
        GetCondicionMedicaResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_CON_MED
            txRespuesta = output
        }
}
