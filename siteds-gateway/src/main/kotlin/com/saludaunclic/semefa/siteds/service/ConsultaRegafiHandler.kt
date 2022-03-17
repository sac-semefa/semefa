package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.ResponseInRegAfi271
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.service.RegAfi270Service
import pe.gob.susalud.jr.transaccion.susalud.service.RegAfi271Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaRegAfiliadosRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaRegAfiliadosResponse

@Service
class ConsultaRegafiHandler(private val sitedsValidator: SitedsValidator,
                            private val regAfi270Service: RegAfi270Service,
                            private val regAfi271Service: RegAfi271Service,
                            private val handlerProvider: HandlerProvider
): StringOutputSitedsHandler<GetConsultaRegAfiliadosRequest, GetConsultaRegAfiliadosResponse>() {
    override fun handleRequest(request: GetConsultaRegAfiliadosRequest): String {
        sitedsValidator.validate(request)

        val inRegAfi270 = regAfi270Service.x12NToBean(request.txPeticion)
        val bean = sendBean(handlerProvider.resolvePath(this), inRegAfi270, ResponseInRegAfi271::class.java)

        return regAfi271Service.beanToX12N(bean.data)
    }

    override fun createResponse(errorCode: String, output: String): GetConsultaRegAfiliadosResponse =
        GetConsultaRegAfiliadosResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_REGAFI
            txRespuesta = output
        }
}
