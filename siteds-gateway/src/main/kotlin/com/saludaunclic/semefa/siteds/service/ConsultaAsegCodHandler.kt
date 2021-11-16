package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.bean.InConCod271
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConCod271Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegCodRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegCodResponse

@Service
class ConsultaAsegCodHandler(private val sitedsValidator: SitedsValidator,
                             private val sitedsProperties: SitedsProperties,
                             private val conAse270Service: ConAse270Service,
                             private val conCod271Service: ConCod271Service
): BaseSitedsHandler<GetConsultaAsegCodRequest, GetConsultaAsegCodResponse>() {
    companion object {
        const val PATH: String = "/conasecod"
    }

    override fun handleRequest(request: GetConsultaAsegCodRequest) {
        sitedsValidator.validateConsultaAsegCod(request)

        val inConAse270: InConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inConAse270)

        val bean: InConCod271 = sendBean(sitedsProperties.sacUrl + PATH, inConAse270, InConCod271::class.java)
        val x12 = conCod271Service.beanToX12N(bean)
        logConvertResponse(logger, bean, x12)
    }

    override fun createResponse(errorCode: String): GetConsultaAsegCodResponse =
        GetConsultaAsegCodResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_CON_COD
        }
}
