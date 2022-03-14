package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ResSctr
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ResSctrService
import pe.gob.susalud.ws.siteds.schemas.GetRegistroDecAccidenteRequest
import pe.gob.susalud.ws.siteds.schemas.GetRegistroDecAccidenteResponse

@Service
class RegistroDecAccidenteHandler(private val sitedsValidator: SitedsValidator,
                                  private val conAse270Service: ConAse270Service,
                                  private val in271ResSctrService: In271ResSctrService,
                                  private val handlerProvider: HandlerProvider,
                                  sitedsProperties: SitedsProperties
): StringOutputSitedsHandler<GetRegistroDecAccidenteRequest, GetRegistroDecAccidenteResponse>(sitedsProperties) {
    override fun handleRequest(request: GetRegistroDecAccidenteRequest): String {
        sitedsValidator.validate(request)

        val inConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inConAse270)

        val bean = sendBean(handlerProvider.resolvePath(this), inConAse270, In271ResSctr::class.java)
        val x12 = in271ResSctrService.In271ResSctr_ToX12N(bean)
        logConvertResponse(logger, bean, x12)

        return x12
    }

    override fun createResponse(errorCode: String, output: String): GetRegistroDecAccidenteResponse =
        GetRegistroDecAccidenteResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_RES_SCTR
            txRespuesta = output
        }
}
