package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.ErrorCodes
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaResponse

@Service
class GetConsultaEntVinculadaHandler(private val sitedsValidator: SitedsValidator)
    : SitedsHandler<GetConsultaEntVinculadaRequest, GetConsultaEntVinculadaResponse> {
    override fun handleRequest(request: GetConsultaEntVinculadaRequest):
        GetConsultaEntVinculadaResponse {
        sitedsValidator.validateConsultaEntVinculada(request)
        return createResponse(request, ErrorCodes.NO_ERROR)
    }

    override fun createResponse(
        request: GetConsultaEntVinculadaRequest,
        errorCode: String)
    : GetConsultaEntVinculadaResponse = GetConsultaEntVinculadaResponse()
        .apply {
            coError = errorCode
            txNombre = request.txNombre
            coIafa = request.coIafa
        }
}
