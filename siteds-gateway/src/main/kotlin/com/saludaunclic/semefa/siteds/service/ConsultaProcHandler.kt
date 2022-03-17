package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.ResponseInConProc271
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ConProcService
import pe.gob.susalud.ws.siteds.schemas.GetConsultaProcRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaProcResponse

@Service
class ConsultaProcHandler(private val sitedsValidator: SitedsValidator,
                          private val conAse270Service: ConAse270Service,
                          private val in271ConProcService: In271ConProcService,
                          private val handlerProvider: HandlerProvider
): StringOutputSitedsHandler<GetConsultaProcRequest, GetConsultaProcResponse>() {
    override fun handleRequest(request: GetConsultaProcRequest): String {
        sitedsValidator.validate(request)

        val inConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        val bean = sendBean(handlerProvider.resolvePath(this), inConAse270, ResponseInConProc271::class.java)

        return in271ConProcService.beanToX12N(bean.data)
    }

    override fun createResponse(errorCode: String, output: String): GetConsultaProcResponse =
        GetConsultaProcResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_CON_PROC
            txRespuesta = output
        }
}
