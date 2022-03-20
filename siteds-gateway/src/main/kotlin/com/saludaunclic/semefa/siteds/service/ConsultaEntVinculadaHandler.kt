package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.model.ResponseInResEntVinc278
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InConEntVinc278
import pe.gob.susalud.jr.transaccion.susalud.bean.InResEntVinc278
import pe.gob.susalud.jr.transaccion.susalud.service.ConEntVinc278Service
import pe.gob.susalud.jr.transaccion.susalud.service.ResEntVinc278Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaResponse

@Service
class ConsultaEntVinculadaHandler(private val conEntVinc278Service: ConEntVinc278Service,
                                  private val resEntVinc278Service: ResEntVinc278Service
): BaseSitedsHandler2<GetConsultaEntVinculadaRequest,
                     GetConsultaEntVinculadaResponse,
                     InConEntVinc278,
                     InResEntVinc278>() {
    override fun validate(request: GetConsultaEntVinculadaRequest) = sitedsValidator.validate(request)

    override fun extractInput(request: GetConsultaEntVinculadaRequest): InConEntVinc278 =
        conEntVinc278Service.x12NToBean(request.txPeticion)

    override fun <R : Response<InResEntVinc278>> resolveResponseClass(): Class<R> =
        ResponseInResEntVinc278::class.java as Class<R>

    override fun createResponse(output: InResEntVinc278): GetConsultaEntVinculadaResponse =
        GetConsultaEntVinculadaResponse().apply {
            coError = SitedsConstants.ErrorCodes.NO_ERROR
            coIafa = output.idReceptor
            txNombre = Transactions.RES_278_RES_ENT_VINC
            txRespuesta = resEntVinc278Service.beanToX12N(output)
        }

    override fun createErrorResponse(errorCode: String, request: GetConsultaEntVinculadaRequest): GetConsultaEntVinculadaResponse =
        GetConsultaEntVinculadaResponse().apply {
            coError = errorCode
            coIafa = request.coIafa
            txNombre = Transactions.RES_278_RES_ENT_VINC
            txRespuesta = StringUtils.EMPTY
        }
}
