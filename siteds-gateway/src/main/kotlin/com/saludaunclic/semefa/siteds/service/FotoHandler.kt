package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.common.service.DateService
import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.service.FotoHandler.FotoOutput
import org.springframework.stereotype.Service
import pe.gob.susalud.ws.siteds.schemas.GetFotoRequest
import pe.gob.susalud.ws.siteds.schemas.GetFotoResponse

@Service
class FotoHandler(private val dateService: DateService)
    : NoopHandler<GetFotoRequest, GetFotoResponse, Any, FotoOutput>() {
    override fun getOutput(request: GetFotoRequest): FotoOutput =
        FotoOutput(request.coIafa, request.coAfPaciente, dateService.formatDate(dateService.now()), ByteArray(0))

    override fun createResponse(output: FotoOutput): GetFotoResponse =
        GetFotoResponse().apply {
            coError = SitedsConstants.ErrorCodes.NO_ERROR
            coIafa = output.coIafa
            coAfPaciente = output.coAfPaciente
            txFecha = dateService.formatDate(dateService.now())
            imFoto = ByteArray(0)
        }

    override fun createErrorResponse(errorCode: String, request: GetFotoRequest): GetFotoResponse =
        GetFotoResponse().apply {
            coError = errorCode
            coIafa = request.coIafa
            coAfPaciente =request.coAfPaciente
            txFecha = dateService.formatDate(dateService.now())
            imFoto = ByteArray(0)
        }

    class FotoOutput(val coIafa: String,
                     val coAfPaciente: String,
                     val txFecha: String,
                     val imFoto: ByteArray)
}
