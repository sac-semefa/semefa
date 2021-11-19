package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ConObs
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.bean.InConMed271
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ConObsService
import pe.gob.susalud.ws.siteds.schemas.GetCondicionMedicaRequest
import pe.gob.susalud.ws.siteds.schemas.GetCondicionMedicaResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaObservacionRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaObservacionResponse

@Service
class ConsultaObservacionHandler(private val sitedsValidator: SitedsValidator,
                                 private val sitedsProperties: SitedsProperties,
                                 private val conAse270Service: ConAse270Service,
                                 private val in271ConObsService: In271ConObsService
): BaseSitedsHandler<GetConsultaObservacionRequest, GetConsultaObservacionResponse>() {
    companion object {
        const val PATH: String = "/conobserv"
    }

    override fun handleRequest(request: GetConsultaObservacionRequest) {
        sitedsValidator.validate(request)

        val inConAse270: InConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inConAse270)

        val bean: In271ConObs = sendBean(sitedsProperties.sacUrl + PATH, inConAse270, In271ConObs::class.java)
        val x12: String = in271ConObsService.beanToX12N(bean)
        logConvertResponse(logger, bean, x12)
    }

    override fun createResponse(errorCode: String): GetConsultaObservacionResponse =
        GetConsultaObservacionResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_CON_OBS
        }
}
