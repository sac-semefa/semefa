package com.saludaunclic.semefa.siteds.endpoint

import com.saludaunclic.semefa.siteds.service.CondicionMedicaHandler
import com.saludaunclic.semefa.siteds.service.ConsultaAseSctrHandler
import com.saludaunclic.semefa.siteds.service.ConsultaAsegCodHandler
import com.saludaunclic.semefa.siteds.service.ConsultaAsegNomHandler
import com.saludaunclic.semefa.siteds.service.ConsultaCartaGarantiaHandler
import com.saludaunclic.semefa.siteds.service.ConsultaDatosAdiHandler
import com.saludaunclic.semefa.siteds.service.ConsultaDerivaHandler
import com.saludaunclic.semefa.siteds.service.ConsultaEntVinculadaHandler
import com.saludaunclic.semefa.siteds.service.ConsultaObservacionHandler
import com.saludaunclic.semefa.siteds.service.ConsultaProcHandler
import com.saludaunclic.semefa.siteds.service.ConsultaRegafiHandler
import com.saludaunclic.semefa.siteds.service.FotoHandler
import com.saludaunclic.semefa.siteds.service.NumAutorizacionHandler
import com.saludaunclic.semefa.siteds.service.RegistroDecAccidenteHandler
import org.apache.cxf.feature.Features
import org.springframework.stereotype.Service
import pe.gob.susalud.ws.siteds.schemas.GetCondicionMedicaRequest
import pe.gob.susalud.ws.siteds.schemas.GetCondicionMedicaResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegCodRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegCodResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDatosAdiRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDatosAdiResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDerivaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDerivaResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaObservacionRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaObservacionResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaProcRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaProcResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaRegAfiliadosRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaRegAfiliadosResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaSCTRRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaSCTRResponse
import pe.gob.susalud.ws.siteds.schemas.GetConsultaxCartaGarantiaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaxCartaGarantiaResponse
import pe.gob.susalud.ws.siteds.schemas.GetFotoRequest
import pe.gob.susalud.ws.siteds.schemas.GetFotoResponse
import pe.gob.susalud.ws.siteds.schemas.GetNumAutorizacionRequest
import pe.gob.susalud.ws.siteds.schemas.GetNumAutorizacionResponse
import pe.gob.susalud.ws.siteds.schemas.GetRegistroDecAccidenteRequest
import pe.gob.susalud.ws.siteds.schemas.GetRegistroDecAccidenteResponse
import pe.gob.susalud.ws.siteds.schemas.SitedsService

@Service
@Features(features = [ "org.apache.cxf.ext.logging.LoggingFeature" ])
class SitedsEndpoint(
    private val consultaEntVinculadaHandler: ConsultaEntVinculadaHandler,
    private val consultaAsegNomHandler: ConsultaAsegNomHandler,
    private val consultaAsegCodHandler: ConsultaAsegCodHandler,
    private val consultaRegafiHandler: ConsultaRegafiHandler,
    private val consultaAsegSctrHandler: ConsultaAseSctrHandler,
    private val registroDecAccidenteHandler: RegistroDecAccidenteHandler,
    private val consultaDerivaHandler: ConsultaDerivaHandler,
    private val consultaProcHandler: ConsultaProcHandler,
    private val consultaDatosAdiHandler: ConsultaDatosAdiHandler,
    private val condicionMedicaHandler: CondicionMedicaHandler,
    private val consultaObservacionHandler: ConsultaObservacionHandler,
    private val numAutorizacionHandler: NumAutorizacionHandler,
    private val consultaCartaGarantiaHandler: ConsultaCartaGarantiaHandler,
    private val fotoHandler: FotoHandler
) : SitedsService {
    override fun getConsultaEntVinculada(getConsultaEntVinculadaRequest: GetConsultaEntVinculadaRequest):
        GetConsultaEntVinculadaResponse = consultaEntVinculadaHandler.handle(getConsultaEntVinculadaRequest)

    override fun getConsultaAsegCod(getConsultaAsegCodRequest: GetConsultaAsegCodRequest): GetConsultaAsegCodResponse =
        consultaAsegCodHandler.handle(getConsultaAsegCodRequest)

    override fun getRegistroDecAccidente(getRegistroDecAccidenteRequest: GetRegistroDecAccidenteRequest):
        GetRegistroDecAccidenteResponse = registroDecAccidenteHandler.handle(getRegistroDecAccidenteRequest)

    override fun getConsultaRegAfiliados(getConsultaRegAfiliadosRequest: GetConsultaRegAfiliadosRequest)
        : GetConsultaRegAfiliadosResponse = consultaRegafiHandler.handle(getConsultaRegAfiliadosRequest)

    override fun getCondicionMedica(getCondicionMedicaRequest: GetCondicionMedicaRequest): GetCondicionMedicaResponse =
        condicionMedicaHandler.handle(getCondicionMedicaRequest)

    override fun getNumAutorizacion(getNumAutorizacionRequest: GetNumAutorizacionRequest): GetNumAutorizacionResponse =
        numAutorizacionHandler.handle(getNumAutorizacionRequest)

    override fun getConsultaSCTR(getConsultaSCTRRequest: GetConsultaSCTRRequest): GetConsultaSCTRResponse =
        consultaAsegSctrHandler.handle(getConsultaSCTRRequest)

    override fun getConsultaxCartaGarantia(getConsultaxCartaGarantiaRequest: GetConsultaxCartaGarantiaRequest):
        GetConsultaxCartaGarantiaResponse = consultaCartaGarantiaHandler.handle(getConsultaxCartaGarantiaRequest)

    override fun getConsultaAsegNom(getConsultaAsegNomRequest: GetConsultaAsegNomRequest): GetConsultaAsegNomResponse =
        consultaAsegNomHandler.handle(getConsultaAsegNomRequest)

    override fun getConsultaDeriva(getConsultaDerivaRequest: GetConsultaDerivaRequest): GetConsultaDerivaResponse =
        consultaDerivaHandler.handle(getConsultaDerivaRequest)

    override fun getConsultaProc(getConsultaProcRequest: GetConsultaProcRequest): GetConsultaProcResponse =
        consultaProcHandler.handle(getConsultaProcRequest)

    override fun getConsultaObservacion(getConsultaObservacionRequest: GetConsultaObservacionRequest):
        GetConsultaObservacionResponse = consultaObservacionHandler.handle(getConsultaObservacionRequest)

    override fun getConsultaDatosAdi(getConsultaDatosAdiRequest: GetConsultaDatosAdiRequest):
        GetConsultaDatosAdiResponse = consultaDatosAdiHandler.handle(getConsultaDatosAdiRequest)

    override fun getFoto(getFotoRequest: GetFotoRequest): GetFotoResponse = fotoHandler.handle(getFotoRequest)
}
