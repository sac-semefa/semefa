package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.model.ResponseIn271ConDtad
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ConDtad
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ConDtadService
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDatosAdiRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDatosAdiResponse

@Service
class ConsultaDatosAdiHandler(private val conAse270Service: ConAse270Service,
                              private val in271ConDtadService: In271ConDtadService
): BaseSitedsHandler<GetConsultaDatosAdiRequest, GetConsultaDatosAdiResponse, InConAse270, In271ConDtad>() {
    override fun validate(request: GetConsultaDatosAdiRequest) = sitedsValidator.validate(request)

    override fun extractInput(request: GetConsultaDatosAdiRequest): InConAse270 =
        conAse270Service.x12NToBean(request.txPeticion)

    override fun <R : Response<In271ConDtad>> resolveResponseClass(): Class<R> =
        ResponseIn271ConDtad::class.java as Class<R>

    override fun createResponse(output: In271ConDtad): GetConsultaDatosAdiResponse =
        GetConsultaDatosAdiResponse().apply {
            coError = SitedsConstants.ErrorCodes.NO_ERROR
            coIafa = output.idReceptor
            txNombre = Transactions.RES_271_CON_DTAD
            txRespuesta = in271ConDtadService.beanToX12N(output)
        }

    override fun createErrorResponse(errorCode: String,
                                     request: GetConsultaDatosAdiRequest,
                                     output: In271ConDtad): GetConsultaDatosAdiResponse =
        GetConsultaDatosAdiResponse().apply {
            coError = errorCode
            coIafa = request.coIafa
            txNombre = Transactions.RES_271_CON_DTAD
            txRespuesta = StringUtils.EMPTY
        }
}
