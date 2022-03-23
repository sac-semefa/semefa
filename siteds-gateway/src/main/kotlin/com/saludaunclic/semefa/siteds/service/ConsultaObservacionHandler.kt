package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.model.ResponseIn271ConObs
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ConObs
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ConObsService
import pe.gob.susalud.ws.siteds.schemas.GetConsultaObservacionRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaObservacionResponse

@Service
class ConsultaObservacionHandler(private val conAse270Service: ConAse270Service,
                                 private val in271ConObsService: In271ConObsService
): BaseSitedsHandler<GetConsultaObservacionRequest, GetConsultaObservacionResponse, InConAse270, In271ConObs>() {
    override fun validate(request: GetConsultaObservacionRequest) = sitedsValidator.validate(request)

    override fun extractInput(request: GetConsultaObservacionRequest): InConAse270 =
        conAse270Service.x12NToBean(request.txPeticion)

    override fun <R : Response<In271ConObs>> resolveResponseClass(): Class<R> =
        ResponseIn271ConObs::class.java as Class<R>

    override fun createResponse(output: In271ConObs): GetConsultaObservacionResponse =
        GetConsultaObservacionResponse().apply {
            coError = SitedsConstants.ErrorCodes.NO_ERROR
            coIafa = output.idReceptor
            txNombre = Transactions.RES_271_CON_OBS
            txRespuesta = in271ConObsService.beanToX12N(output)
            rptObs = StringUtils.EMPTY
        }

    override fun createErrorResponse(errorCode: String,
                                     request: GetConsultaObservacionRequest): GetConsultaObservacionResponse =
        GetConsultaObservacionResponse().apply {
            coError = errorCode
            coIafa = request.coIafa
            txNombre = Transactions.RES_271_CON_OBS
            txRespuesta = StringUtils.EMPTY
            rptObs = StringUtils.EMPTY
        }
}
