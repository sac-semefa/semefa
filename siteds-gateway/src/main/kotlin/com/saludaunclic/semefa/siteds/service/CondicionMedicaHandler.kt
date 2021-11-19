package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.bean.InConMed271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConNom271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConProc271
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConMed271Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConNom271Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ConProcService
import pe.gob.susalud.ws.siteds.schemas.GetCondicionMedicaRequest
import pe.gob.susalud.ws.siteds.schemas.GetCondicionMedicaResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaProcRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaProcResponse

@Service
class CondicionMedicaHandler(private val sitedsValidator: SitedsValidator,
                             private val sitedsProperties: SitedsProperties,
                             private val conAse270Service: ConAse270Service,
                             private val conMed271Service: ConMed271Service
): BaseSitedsHandler<GetCondicionMedicaRequest, GetCondicionMedicaResponse>() {
    companion object {
        const val PATH: String = "/conmedica"
    }

    override fun handleRequest(request: GetCondicionMedicaRequest) {
        sitedsValidator.validate(request)

        val inConAse270: InConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inConAse270)

        val bean: InConMed271 = sendBean(sitedsProperties.sacUrl + PATH, inConAse270, InConMed271::class.java)
        val x12: String = conMed271Service.beanToX12N(bean)
        logConvertResponse(logger, bean, x12)
    }

    override fun createResponse(errorCode: String): GetCondicionMedicaResponse =
        GetCondicionMedicaResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_CON_MED
        }
}
