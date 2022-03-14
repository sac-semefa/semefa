package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InRegAfi271
import pe.gob.susalud.jr.transaccion.susalud.service.RegAfi270Service
import pe.gob.susalud.jr.transaccion.susalud.service.RegAfi271Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaRegAfiliadosRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaRegAfiliadosResponse

@Service
class ConsultaRegafiHandler(private val sitedsValidator: SitedsValidator,
                            private val regAfi270Service: RegAfi270Service,
                            private val regAfi271Service: RegAfi271Service,
                            private val handlerProvider: HandlerProvider,
                            sitedsProperties: SitedsProperties
): StringOutputSitedsHandler<GetConsultaRegAfiliadosRequest, GetConsultaRegAfiliadosResponse>(sitedsProperties) {
    override fun handleRequest(request: GetConsultaRegAfiliadosRequest): String {
        sitedsValidator.validate(request)

        val inRegAfi270 = regAfi270Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inRegAfi270)

        val inRegAfi271 = sendBean(handlerProvider.resolvePath(this), inRegAfi270, InRegAfi271::class.java)
        val x12 = regAfi271Service.beanToX12N(inRegAfi271)
        logConvertResponse(logger, inRegAfi271, x12)

        return x12
    }

    override fun createResponse(errorCode: String, output: String): GetConsultaRegAfiliadosResponse =
        GetConsultaRegAfiliadosResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_REGAFI
            txRespuesta = output
        }
}
