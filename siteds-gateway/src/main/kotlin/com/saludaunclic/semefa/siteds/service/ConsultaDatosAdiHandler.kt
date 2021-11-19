package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ConDtad
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.bean.InConNom271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConProc271
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConNom271Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ConDtadService
import pe.gob.susalud.jr.transaccion.susalud.service.In271ConProcService
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDatosAdiRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDatosAdiResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaProcRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaProcResponse

@Service
class ConsultaDatosAdiHandler(private val sitedsValidator: SitedsValidator,
                              private val sitedsProperties: SitedsProperties,
                              private val conAse270Service: ConAse270Service,
                              private val in271ConDtadService: In271ConDtadService
): BaseSitedsHandler<GetConsultaDatosAdiRequest, GetConsultaDatosAdiResponse>() {
    companion object {
        const val PATH: String = "/condatosadi"
    }

    override fun handleRequest(request: GetConsultaDatosAdiRequest) {
        sitedsValidator.validate(request)

        val inConAse270: InConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inConAse270)

        val bean: In271ConDtad = sendBean(sitedsProperties.sacUrl + PATH, inConAse270, In271ConDtad::class.java)
        val x12: String = in271ConDtadService.beanToX12N(bean)
        logConvertResponse(logger, bean, x12)
    }

    override fun createResponse(errorCode: String): GetConsultaDatosAdiResponse =
        GetConsultaDatosAdiResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_CON_DTAD
        }
}
