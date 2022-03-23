package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.model.ResponseIn997ResAut
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In997ResAut
import pe.gob.susalud.jr.transaccion.susalud.bean.InSolAut271
import pe.gob.susalud.jr.transaccion.susalud.service.In997ResAutService
import pe.gob.susalud.jr.transaccion.susalud.service.SolAut271Service
import pe.gob.susalud.ws.siteds.schemas.GetNumAutorizacionRequest
import pe.gob.susalud.ws.siteds.schemas.GetNumAutorizacionResponse

@Service
class NumAutorizacionHandler(private val solAut271Service: SolAut271Service,
                             private val in997ResAutService: In997ResAutService
): BaseSitedsHandler<GetNumAutorizacionRequest, GetNumAutorizacionResponse, InSolAut271, In997ResAut>() {
    override fun validate(request: GetNumAutorizacionRequest) = sitedsValidator.validate(request)

    override fun extractInput(request: GetNumAutorizacionRequest): InSolAut271 =
        solAut271Service.x12NToBean(request.txPeticion)

    override fun <R : Response<In997ResAut>> resolveResponseClass(): Class<R> =
        ResponseIn997ResAut::class.java as Class<R>

    override fun createResponse(output: In997ResAut): GetNumAutorizacionResponse =
        GetNumAutorizacionResponse().apply {
            coError = parseError(output)
            coIafa = output.idReceptor
            txNombre = Transactions.RES_997_RES_AUT
            txRespuesta = in997ResAutService.beanToX12N(output)
        }

    override fun createErrorResponse(errorCode: String,
                                     request: GetNumAutorizacionRequest,
                                     output: In997ResAut): GetNumAutorizacionResponse =
        GetNumAutorizacionResponse().apply {
            coError = errorCode
            coIafa = request.coIafa
            txNombre = Transactions.RES_997_RES_AUT
            txRespuesta = StringUtils.EMPTY
        }

    fun parseError(output: In997ResAut): String =
        if (output.coError.isBlank()) SitedsConstants.ErrorCodes.NO_ERROR
        else "${output.coError}${output.inCoErrorEncontrado}"
}
