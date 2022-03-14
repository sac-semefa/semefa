package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.service.ConsultaObservacionHandler.ObservacionOutput
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ConObs
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ConObsService
import pe.gob.susalud.ws.siteds.schemas.GetConsultaObservacionRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaObservacionResponse

@Service
class ConsultaObservacionHandler(private val sitedsValidator: SitedsValidator,
                                 private val conAse270Service: ConAse270Service,
                                 private val in271ConObsService: In271ConObsService,
                                 private val handlerProvider: HandlerProvider,
                                 sitedsProperties: SitedsProperties
): BaseSitedsHandler<GetConsultaObservacionRequest,
                     GetConsultaObservacionResponse,
                     ObservacionOutput>(sitedsProperties) {
    override fun handleRequest(request: GetConsultaObservacionRequest): ObservacionOutput {
        sitedsValidator.validate(request)

        val inConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inConAse270)

        val bean = sendBean(handlerProvider.resolvePath(this), inConAse270, In271ConObs::class.java)
        val x12 = in271ConObsService.beanToX12N(bean)
        logConvertResponse(logger, bean, x12)

        return ObservacionOutput(x12, StringUtils.EMPTY)
    }

    override fun createResponse(errorCode: String, output: ObservacionOutput): GetConsultaObservacionResponse =
        GetConsultaObservacionResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_CON_OBS
            txRespuesta = output.response
            rptObs = output.rptObs
        }

    override fun errorOutput(): ObservacionOutput = ObservacionOutput(StringUtils.EMPTY, StringUtils.EMPTY)

    data class ObservacionOutput(val response: String, val rptObs: String)
}
