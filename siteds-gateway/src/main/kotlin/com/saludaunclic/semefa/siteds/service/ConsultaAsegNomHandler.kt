package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.model.ResponseInConNom271
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.bean.InConNom271
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConNom271Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomResponse

@Service
class ConsultaAsegNomHandler(private val conAse270Service: ConAse270Service,
                             private val conNom271Service: ConNom271Service
): BaseSitedsHandler<GetConsultaAsegNomRequest, GetConsultaAsegNomResponse, InConAse270, InConNom271>() {
    override fun validate(request: GetConsultaAsegNomRequest) = sitedsValidator.validate(request)

    override fun extractInput(request: GetConsultaAsegNomRequest): InConAse270 =
        conAse270Service.x12NToBean(request.txPeticion)

    override fun <R : Response<InConNom271>> resolveResponseClass(): Class<R> =
        ResponseInConNom271::class.java as Class<R>

    override fun createResponse(output: InConNom271): GetConsultaAsegNomResponse =
        GetConsultaAsegNomResponse().apply {
            coError = SitedsConstants.ErrorCodes.NO_ERROR
            coIafa = output.idReceptor
            txNombre = Transactions.RES_271_CON_NOM
            txRespuesta = conNom271Service.beanToX12N(output)
        }

    override fun createErrorResponse(errorCode: String,
                                     request: GetConsultaAsegNomRequest,
                                     output: InConNom271): GetConsultaAsegNomResponse =
        GetConsultaAsegNomResponse().apply {
            coError = errorCode
            coIafa = request.coIafa
            txNombre = Transactions.RES_271_CON_NOM
            txRespuesta = StringUtils.EMPTY
        }
}
