package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.common.service.DateService
import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.ErrorCodes
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.throwing.SitedsException
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDerivaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDerivaResponse
import pe.gob.susalud.ws.siteds.schemas.GetFotoRequest
import pe.gob.susalud.ws.siteds.schemas.GetFotoResponse

@Service
class FotoHandler(private val sitedsProperties: SitedsProperties,
                  private val dateService: DateService)
    : BaseSitedsHandler<GetFotoRequest, GetFotoResponse>() {
    override fun handleRequest(request: GetFotoRequest) {}

    override fun createResponse(errorCode: String): GetFotoResponse =
        GetFotoResponse()

    override fun handle(request: GetFotoRequest): GetFotoResponse =
        GetFotoResponse().apply {
            coError = ErrorCodes.TRANSACTION_UNUSED
            coIafa = sitedsProperties.iafaCode
            coAfPaciente = request.coAfPaciente
            txFecha = dateService.formatDate(dateService.now())
            imFoto = ByteArray(0)
        }
}
