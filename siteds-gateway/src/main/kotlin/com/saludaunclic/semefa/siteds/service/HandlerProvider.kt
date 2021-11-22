package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.config.SitedsProperties
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class HandlerProvider(private val sitedsProperties: SitedsProperties,
                      private val handlerPath: MutableMap<String, String> = mutableMapOf()) {
    @PostConstruct
    fun init() {
        handlerPath[CondicionMedicaHandler::class.java.simpleName] = "/conmedica"
        handlerPath[ConsultaAsegCodHandler::class.java.simpleName] = "/conasecod"
        handlerPath[ConsultaAsegNomHandler::class.java.simpleName] = "/conasenom"
        handlerPath[ConsultaAseSctrHandler::class.java.simpleName] = "/conasesctr"
        handlerPath[ConsultaCartaGarantiaHandler::class.java.simpleName] = "/concartaga"
        handlerPath[ConsultaDatosAdiHandler::class.java.simpleName] = "/condatosadi"
        handlerPath[ConsultaDerivaHandler::class.java.simpleName] = StringUtils.EMPTY
        handlerPath[ConsultaEntVinculadaHandler::class.java.simpleName] = "/entvinc"
        handlerPath[ConsultaObservacionHandler::class.java.simpleName] = "/conobserv"
        handlerPath[ConsultaProcHandler::class.java.simpleName] = "/conproc"
        handlerPath[ConsultaRegafiHandler::class.java.simpleName] = "/conregafi"
        handlerPath[NumAutorizacionHandler::class.java.simpleName] = "/numaut"
        handlerPath[RegistroDecAccidenteHandler::class.java.simpleName] = "/regaccidente"
        handlerPath[FotoHandler::class.java.simpleName] = StringUtils.EMPTY
    }

    fun resolvePath(obj: Any): String = sitedsProperties.sacUrl +
        (handlerPath[obj::class.java.simpleName] ?: throw IllegalArgumentException("Invalid provided handler"))
}
