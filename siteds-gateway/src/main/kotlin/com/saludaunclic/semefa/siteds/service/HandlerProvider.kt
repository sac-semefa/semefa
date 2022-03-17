package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.config.SitedsProperties
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class HandlerProvider(private val sitedsProperties: SitedsProperties) {
    private val handlerPath: MutableMap<String, String> = mutableMapOf()

    @PostConstruct
    fun init() {
        handlerPath[ConsultaEntVinculadaHandler::class.java.simpleName] = "/getConsultaEntVinculada"
        handlerPath[ConsultaAsegCodHandler::class.java.simpleName] = "/getConsultaAsegCod"
        handlerPath[RegistroDecAccidenteHandler::class.java.simpleName] = "/getRegistroDecAccidente"
        handlerPath[ConsultaRegafiHandler::class.java.simpleName] = "/getConsultaRegAfiliados"
        handlerPath[CondicionMedicaHandler::class.java.simpleName] = "/getCondicionMedica"
        handlerPath[NumAutorizacionHandler::class.java.simpleName] = "/getNumAutorizacion"
        handlerPath[ConsultaAseSctrHandler::class.java.simpleName] = "/getConsultaSCTR"
        handlerPath[ConsultaCartaGarantiaHandler::class.java.simpleName] = "/getConsultaxCartaGarantia"
        handlerPath[ConsultaAsegNomHandler::class.java.simpleName] = "/getConsultaAsegNom"
        handlerPath[ConsultaDerivaHandler::class.java.simpleName] = "getConsultaDeriva"
        handlerPath[ConsultaProcHandler::class.java.simpleName] = "/getConsultaProc"
        handlerPath[ConsultaObservacionHandler::class.java.simpleName] = "/getConsultaObservacion"
        handlerPath[ConsultaDatosAdiHandler::class.java.simpleName] = "/getConsultaDatosAdi"
        handlerPath[FotoHandler::class.java.simpleName] = StringUtils.EMPTY
    }

    fun resolvePath(obj: Any): String = sitedsProperties.sacUrl +
        (handlerPath[obj::class.java.simpleName] ?: throw IllegalArgumentException("Invalid provided handler"))
}
