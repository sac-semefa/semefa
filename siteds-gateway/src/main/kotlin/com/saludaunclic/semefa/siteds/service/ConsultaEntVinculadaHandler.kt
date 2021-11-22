package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InResEntVinc278
import pe.gob.susalud.jr.transaccion.susalud.service.ConEntVinc278Service
import pe.gob.susalud.jr.transaccion.susalud.service.ResEntVinc278Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaResponse

@Service
class ConsultaEntVinculadaHandler(private val sitedsValidator: SitedsValidator,
                                  private val sitedsProperties: SitedsProperties,
                                  private val conEntVinc278Service: ConEntVinc278Service,
                                  private val resEntVinc278Service: ResEntVinc278Service,
                                  private val handlerProvider: HandlerProvider
): StringOutputSitedsHandler<GetConsultaEntVinculadaRequest, GetConsultaEntVinculadaResponse>() {
    override fun handleRequest(request: GetConsultaEntVinculadaRequest): String {
        sitedsValidator.validate(request)

        val inConEntVinc278 = conEntVinc278Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inConEntVinc278)

        val inResEntVinc278 = sendBean(handlerProvider.resolvePath(this), inConEntVinc278, InResEntVinc278::class.java)
        val x12 = resEntVinc278Service.beanToX12N(inResEntVinc278)
        logConvertResponse(logger, inResEntVinc278, x12)

        return x12
    }

    override fun createResponse(errorCode: String, output: String): GetConsultaEntVinculadaResponse =
        GetConsultaEntVinculadaResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_278_RES_ENT_VINC
            txRespuesta = output
        }
}
