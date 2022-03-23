package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.model.Response
import com.saludaunclic.semefa.siteds.model.ResponseIn278ResCG
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In278ResCG
import pe.gob.susalud.jr.transaccion.susalud.bean.In278SolCG
import pe.gob.susalud.jr.transaccion.susalud.service.In278ResCGService
import pe.gob.susalud.jr.transaccion.susalud.service.In278SolCGService
import pe.gob.susalud.ws.siteds.schemas.GetConsultaxCartaGarantiaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaxCartaGarantiaResponse

@Service
class ConsultaCartaGarantiaHandler(private val in278SolCGService: In278SolCGService,
                                   private val in278ResCGService: In278ResCGService
): BaseSitedsHandler<GetConsultaxCartaGarantiaRequest, GetConsultaxCartaGarantiaResponse, In278SolCG, In278ResCG>() {
    override fun validate(request: GetConsultaxCartaGarantiaRequest) = sitedsValidator.validate(request)

    override fun extractInput(request: GetConsultaxCartaGarantiaRequest) =
        in278SolCGService.x12NToBean(request.txPeticion)

    override fun <R : Response<In278ResCG>> resolveResponseClass(): Class<R> =
        ResponseIn278ResCG::class.java as Class<R>

    override fun createResponse(output: In278ResCG): GetConsultaxCartaGarantiaResponse =
        GetConsultaxCartaGarantiaResponse().apply {
            coError = SitedsConstants.ErrorCodes.NO_ERROR
            coIafa = output.idReceptor
            txNombre = Transactions.RES_278_RES_CG
            txRespuesta = in278ResCGService.beanToX12N(output)
        }

    override fun createErrorResponse(errorCode: String,
                                     request: GetConsultaxCartaGarantiaRequest,
                                     output: In278ResCG): GetConsultaxCartaGarantiaResponse =
        GetConsultaxCartaGarantiaResponse().apply {
            coError = errorCode
            coIafa = request.coIafa
            txNombre = Transactions.RES_278_RES_CG
            txRespuesta = StringUtils.EMPTY

        }
}
