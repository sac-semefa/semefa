package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.model.ResponseInRegAfi271
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InRegAfi270
import pe.gob.susalud.jr.transaccion.susalud.bean.InRegAfi271
import pe.gob.susalud.jr.transaccion.susalud.service.RegAfi270Service
import pe.gob.susalud.jr.transaccion.susalud.service.RegAfi271Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaRegAfiliadosRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaRegAfiliadosResponse

@Service
class ConsultaRegafiHandler(private val regAfi270Service: RegAfi270Service,
                            private val regAfi271Service: RegAfi271Service
): BaseSitedsHandler<GetConsultaRegAfiliadosRequest, GetConsultaRegAfiliadosResponse, InRegAfi270, InRegAfi271>() {
    override fun validate(request: GetConsultaRegAfiliadosRequest) = sitedsValidator.validate(request)

    override fun extractInput(request: GetConsultaRegAfiliadosRequest): InRegAfi270 =
        regAfi270Service.x12NToBean(request.txPeticion)

    override fun <R : Response<InRegAfi271>> resolveResponseClass(): Class<R> =
        ResponseInRegAfi271::class.java as Class<R>

    override fun createResponse(output: InRegAfi271): GetConsultaRegAfiliadosResponse =
        GetConsultaRegAfiliadosResponse().apply {
            coError = SitedsConstants.ErrorCodes.NO_ERROR
            coIafa = output.idReceptor
            txNombre = Transactions.RES_271_REGAFI
            txRespuesta = regAfi271Service.beanToX12N(output)
        }

    override fun createErrorResponse(errorCode: String,
                                     request: GetConsultaRegAfiliadosRequest): GetConsultaRegAfiliadosResponse =
        GetConsultaRegAfiliadosResponse().apply {
            coError = errorCode
            coIafa = request.coIafa
            txNombre = Transactions.RES_271_REGAFI
            txRespuesta = StringUtils.EMPTY
        }
}
