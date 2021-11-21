package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.common.service.DateService
import com.saludaunclic.semefa.siteds.SitedsConstants.ErrorCodes
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.service.FotoHandler.FotoOutput
import org.springframework.stereotype.Service
import pe.gob.susalud.ws.siteds.schemas.GetFotoRequest
import pe.gob.susalud.ws.siteds.schemas.GetFotoResponse

@Service
class FotoHandler(private val sitedsProperties: SitedsProperties,
                  private val dateService: DateService)
    : SitedsHandler<GetFotoRequest, GetFotoResponse, FotoOutput> {
    fun handle(request: GetFotoRequest): GetFotoResponse =
        createResponse(ErrorCodes.TRANSACTION_UNUSED, handleRequest(request))

    override fun handleRequest(request: GetFotoRequest): FotoOutput =
        with(request) {
            FotoOutput(coAfPaciente, dateService.formatDate(dateService.now()), ByteArray(0))
        }

    override fun createResponse(errorCode: String, output: FotoOutput): GetFotoResponse =
        GetFotoResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            coAfPaciente = output.coAfPaciente
            txFecha = dateService.formatDate(dateService.now())
            imFoto = ByteArray(0)
        }

    class FotoOutput(val coAfPaciente: String, val txFecha: String, val imFoto: ByteArray)
}
