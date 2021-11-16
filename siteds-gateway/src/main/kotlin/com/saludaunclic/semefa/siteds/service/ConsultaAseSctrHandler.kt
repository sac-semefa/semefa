package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ResSctr
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ResSctrService
import pe.gob.susalud.ws.siteds.schemas.GetConsultaSCTRRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaSCTRResponse

@Service
class ConsultaAseSctrHandler(private val sitedsValidator: SitedsValidator,
                             private val sitedsProperties: SitedsProperties,
                             private val conAse270Service: ConAse270Service,
                             private val in271ResSctrService: In271ResSctrService
): BaseSitedsHandler<GetConsultaSCTRRequest, GetConsultaSCTRResponse>() {
    companion object {
        const val PATH: String = "/conasesctr"
    }

    override fun handleRequest(request: GetConsultaSCTRRequest) {
        sitedsValidator.validateConsultaAsegSctr(request)

        val inConAse270: InConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inConAse270)

        val bean: In271ResSctr = sendBean(sitedsProperties.sacUrl + PATH, inConAse270, In271ResSctr::class.java)
        val x12: String = in271ResSctrService.In271ResSctr_ToX12N(bean)
        logConvertResponse(logger, bean, x12)
    }

    override fun createResponse(errorCode: String): GetConsultaSCTRResponse =
        GetConsultaSCTRResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_RES_SCTR
        }
}
