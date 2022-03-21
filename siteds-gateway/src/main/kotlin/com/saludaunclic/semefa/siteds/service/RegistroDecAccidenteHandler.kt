package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.model.ResponseIn271ResSctr
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ResSctr
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ResSctrService
import pe.gob.susalud.ws.siteds.schemas.GetRegistroDecAccidenteRequest
import pe.gob.susalud.ws.siteds.schemas.GetRegistroDecAccidenteResponse

@Service
class RegistroDecAccidenteHandler(private val conAse270Service: ConAse270Service,
                                  private val in271ResSctrService: In271ResSctrService
): BaseSitedsHandler<GetRegistroDecAccidenteRequest, GetRegistroDecAccidenteResponse, InConAse270, In271ResSctr>() {
    override fun validate(request: GetRegistroDecAccidenteRequest) = sitedsValidator.validate(request)

    override fun extractInput(request: GetRegistroDecAccidenteRequest): InConAse270 =
        conAse270Service.x12NToBean(request.txPeticion)

    override fun <R : Response<In271ResSctr>> resolveResponseClass(): Class<R> =
        ResponseIn271ResSctr::class.java as Class<R>

    override fun createResponse(output: In271ResSctr): GetRegistroDecAccidenteResponse =
        GetRegistroDecAccidenteResponse().apply {
            coError = SitedsConstants.ErrorCodes.NO_ERROR
            coIafa = output.idReceptor
            txNombre = Transactions.RES_271_RES_SCTR
            txRespuesta = in271ResSctrService.In271ResSctr_ToX12N(output)
        }

    override fun createErrorResponse(errorCode: String,
                                     request: GetRegistroDecAccidenteRequest): GetRegistroDecAccidenteResponse =
        GetRegistroDecAccidenteResponse().apply {
            coError = SitedsConstants.ErrorCodes.NO_ERROR
            coIafa = request.coIafa
            txNombre = Transactions.RES_271_RES_SCTR
            txRespuesta = StringUtils.EMPTY
        }
}
