package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.model.ResponseInConMed271
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.bean.InConMed271
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConMed271Service
import pe.gob.susalud.ws.siteds.schemas.GetCondicionMedicaRequest
import pe.gob.susalud.ws.siteds.schemas.GetCondicionMedicaResponse

@Service
class CondicionMedicaHandler(private val conAse270Service: ConAse270Service,
                             private val conMed271Service: ConMed271Service
): BaseSitedsHandler<GetCondicionMedicaRequest, GetCondicionMedicaResponse, InConAse270, InConMed271>() {
    override fun validate(request: GetCondicionMedicaRequest) = sitedsValidator.validate(request)

    override fun extractInput(request: GetCondicionMedicaRequest): InConAse270 =
        conAse270Service.x12NToBean(request.txPeticion)

    override fun <R : Response<InConMed271>> resolveResponseClass(): Class<R> =
        ResponseInConMed271::class.java as Class<R>

    override fun createResponse(output: InConMed271): GetCondicionMedicaResponse =
        GetCondicionMedicaResponse().apply {
            coError = SitedsConstants.ErrorCodes.NO_ERROR
            coIafa = output.idReceptor
            txNombre = Transactions.RES_271_CON_MED
            txRespuesta = conMed271Service.beanToX12N(output)
        }

    override fun createErrorResponse(errorCode: String,
                                     request: GetCondicionMedicaRequest,
                                     output: InConMed271): GetCondicionMedicaResponse =
        GetCondicionMedicaResponse().apply {
            coError = errorCode
            coIafa = request.coIafa
            txNombre = Transactions.RES_271_CON_MED
            txRespuesta = StringUtils.EMPTY
        }
}
