package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.ResponseInResEntVinc278
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConEntVinc278Service
import pe.gob.susalud.jr.transaccion.susalud.service.ResEntVinc278Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaResponse

@Service
class ConsultaEntVinculadaHandler(private val sitedsValidator: SitedsValidator,
                                  private val conEntVinc278Service: ConEntVinc278Service,
                                  private val resEntVinc278Service: ResEntVinc278Service,
                                  private val handlerProvider: HandlerProvider
): StringOutputSitedsHandler<GetConsultaEntVinculadaRequest, GetConsultaEntVinculadaResponse>() {
    override fun handleRequest(request: GetConsultaEntVinculadaRequest): String {
        sitedsValidator.validate(request)

        val inConEntVinc278 = conEntVinc278Service.x12NToBean(request.txPeticion)
        val bean = sendBean(handlerProvider.resolvePath(this), inConEntVinc278, ResponseInResEntVinc278::class.java)

        return resEntVinc278Service.beanToX12N(bean.data)
    }

    override fun createResponse(errorCode: String, output: String): GetConsultaEntVinculadaResponse =
        GetConsultaEntVinculadaResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_278_RES_ENT_VINC
            txRespuesta = output
        }
}
