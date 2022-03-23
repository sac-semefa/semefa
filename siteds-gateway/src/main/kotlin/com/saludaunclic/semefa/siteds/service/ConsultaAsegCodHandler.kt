package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.model.ResponseInConCod271
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.bean.InConCod271
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConCod271Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegCodRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegCodResponse

@Service
class ConsultaAsegCodHandler(private val conAse270Service: ConAse270Service,
                             private val conCod271Service: ConCod271Service
): BaseSitedsHandler<GetConsultaAsegCodRequest, GetConsultaAsegCodResponse, InConAse270, InConCod271>() {
    override fun validate(request: GetConsultaAsegCodRequest) = sitedsValidator.validate(request)

    override fun extractInput(request: GetConsultaAsegCodRequest): InConAse270 =
        conAse270Service.x12NToBean(request.txPeticion)

    override fun <R : Response<InConCod271>> resolveResponseClass(): Class<R> =
        ResponseInConCod271::class.java as Class<R>

    override fun createResponse(output: InConCod271): GetConsultaAsegCodResponse =
        GetConsultaAsegCodResponse().apply {
            coError = SitedsConstants.ErrorCodes.NO_ERROR
            coIafa = output.idReceptor
            txNombre = Transactions.RES_271_CON_COD
            txRespuesta = conCod271Service.beanToX12N(output)
        }

    override fun createErrorResponse(errorCode: String,
                                     request: GetConsultaAsegCodRequest,
                                     output: InConCod271): GetConsultaAsegCodResponse =
        GetConsultaAsegCodResponse().apply {
            coError = errorCode
            coIafa = request.coIafa
            txNombre = Transactions.RES_271_CON_COD
            txRespuesta = StringUtils.EMPTY
        }
}
