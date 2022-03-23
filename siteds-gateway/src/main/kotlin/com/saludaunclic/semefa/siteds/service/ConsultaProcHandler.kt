package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.model.ResponseInConProc271
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.bean.InConProc271
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ConProcService
import pe.gob.susalud.ws.siteds.schemas.GetConsultaProcRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaProcResponse

@Service
class ConsultaProcHandler(private val conAse270Service: ConAse270Service,
                          private val in271ConProcService: In271ConProcService
): BaseSitedsHandler<GetConsultaProcRequest, GetConsultaProcResponse, InConAse270, InConProc271>() {
    override fun validate(request: GetConsultaProcRequest) = sitedsValidator.validate(request)

    override fun extractInput(request: GetConsultaProcRequest): InConAse270 =
        conAse270Service.x12NToBean(request.txPeticion)

    override fun <R : Response<InConProc271>> resolveResponseClass(): Class<R> =
        ResponseInConProc271::class.java as Class<R>

    override fun createResponse(output: InConProc271): GetConsultaProcResponse =
        GetConsultaProcResponse().apply {
            coError = SitedsConstants.ErrorCodes.NO_ERROR
            coIafa = output.idReceptor
            txNombre = Transactions.RES_271_CON_PROC
            txRespuesta = in271ConProcService.beanToX12N(output)
        }

    override fun createErrorResponse(errorCode: String,
                                     request: GetConsultaProcRequest,
                                     output: InConProc271): GetConsultaProcResponse =
        GetConsultaProcResponse().apply {
            coError = errorCode
            coIafa = request.coIafa
            txNombre = Transactions.RES_271_CON_PROC
            txRespuesta = StringUtils.EMPTY
        }
}
