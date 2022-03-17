package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.ResponseInConCod271
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConCod271Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegCodRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegCodResponse

@Service
class ConsultaAsegCodHandler(private val sitedsValidator: SitedsValidator,
                             private val conAse270Service: ConAse270Service,
                             private val conCod271Service: ConCod271Service,
                             private val handlerProvider: HandlerProvider
): StringOutputSitedsHandler<GetConsultaAsegCodRequest, GetConsultaAsegCodResponse>() {
    override fun handleRequest(request: GetConsultaAsegCodRequest): String {
        sitedsValidator.validate(request)

        val inConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inConAse270)

        val bean = sendBean(handlerProvider.resolvePath(this), inConAse270, ResponseInConCod271::class.java)
        val x12 = conCod271Service.beanToX12N(bean.data)
        logConvertResponse(logger, bean, x12)

        return x12
    }

    override fun createResponse(errorCode: String, output: String): GetConsultaAsegCodResponse =
        GetConsultaAsegCodResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_CON_COD
            txRespuesta = output
        }
}
