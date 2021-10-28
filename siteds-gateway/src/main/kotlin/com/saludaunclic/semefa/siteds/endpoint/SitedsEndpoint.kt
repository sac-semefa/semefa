package com.saludaunclic.semefa.siteds.endpoint

import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import com.saludaunclic.semefa.siteds.mapper.SitedsMapper
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
class SitedsEndpoint(private val sitedsService: com.saludaunclic.semefa.siteds.service.sac.SitedsService,
                     private val sitedsValidator: SitedsValidator,
                     private val sitedsMapper: SitedsMapper) : SitedsService {
    override fun getConsultaRegAfiliados(getConsultaRegAfiliadosRequest: GetConsultaRegAfiliadosRequest)
    : GetConsultaRegAfiliadosResponse {
        sitedsValidator.validateConsultaRegAfiliadosRequest(getConsultaRegAfiliadosRequest)
        return GetConsultaRegAfiliadosResponse()
    }

    override fun getConsultaAsegNom(getConsultaAsegNomRequest: GetConsultaAsegNomRequest): GetConsultaAsegNomResponse {
        TODO("Not yet implemented")
    }

    override fun getConsultaDatosAdi(getConsultaDatosAdiRequest: GetConsultaDatosAdiRequest): GetConsultaDatosAdiResponse {
        TODO("Not yet implemented")
    }

    override fun getNumAutorizacion(getNumAutorizacionRequest: GetNumAutorizacionRequest): GetNumAutorizacionResponse {
        TODO("Not yet implemented")
    }

    override fun getConsultaDeriva(getConsultaDerivaRequest: GetConsultaDerivaRequest): GetConsultaDerivaResponse {
        TODO("Not yet implemented")
    }

    override fun getConsultaSCTR(getConsultaSCTRRequest: GetConsultaSCTRRequest): GetConsultaSCTRResponse {
        TODO("Not yet implemented")
    }

    override fun getCondicionMedica(getCondicionMedicaRequest: GetCondicionMedicaRequest): GetCondicionMedicaResponse {
        TODO("Not yet implemented")
    }

    override fun getFoto(getFotoRequest: GetFotoRequest): GetFotoResponse {
        TODO("Not yet implemented")
    }

    override fun getConsultaxCartaGarantia(getConsultaxCartaGarantiaRequest: GetConsultaxCartaGarantiaRequest): GetConsultaxCartaGarantiaResponse {
        TODO("Not yet implemented")
    }

    override fun getRegistroDecAccidente(getRegistroDecAccidenteRequest: GetRegistroDecAccidenteRequest): GetRegistroDecAccidenteResponse {
        TODO("Not yet implemented")
    }

    override fun getConsultaEntVinculada(getConsultaEntVinculadaRequest: GetConsultaEntVinculadaRequest): GetConsultaEntVinculadaResponse {
        TODO("Not yet implemented")
    }

    override fun getConsultaAsegCod(getConsultaAsegCodRequest: GetConsultaAsegCodRequest): GetConsultaAsegCodResponse {
        TODO("Not yet implemented")
    }

    override fun getConsultaObservacion(getConsultaObservacionRequest: GetConsultaObservacionRequest): GetConsultaObservacionResponse {
        TODO("Not yet implemented")
    }

    override fun getConsultaProc(getConsultaProcRequest: GetConsultaProcRequest): GetConsultaProcResponse {
        TODO("Not yet implemented")
    }
}