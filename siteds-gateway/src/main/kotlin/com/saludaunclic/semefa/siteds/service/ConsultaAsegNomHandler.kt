package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InConNom271
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConNom271Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomResponse

@Service
class ConsultaAsegNomHandler(private val sitedsValidator: SitedsValidator,
                             private val conAse270Service: ConAse270Service,
                             private val conNom271Service: ConNom271Service,
                             private val handlerProvider: HandlerProvider,
                             sitedsProperties: SitedsProperties
): StringOutputSitedsHandler<GetConsultaAsegNomRequest, GetConsultaAsegNomResponse>(sitedsProperties) {
    override fun handleRequest(request: GetConsultaAsegNomRequest): String {
        sitedsValidator.validate(request)

        val inConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inConAse270)

        val bean = sendBean(handlerProvider.resolvePath(this), inConAse270, InConNom271::class.java)
        val x12 = conNom271Service.beanToX12N(bean)
        logConvertResponse(logger, bean, x12)

        return x12
    }

    override fun createResponse(errorCode: String, output: String): GetConsultaAsegNomResponse =
        GetConsultaAsegNomResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_CON_NOM
            txRespuesta = output
        }
}
