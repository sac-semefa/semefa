package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.ErrorCodes
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ResDeriva
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDerivaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDerivaResponse

@Service
class ConsultaDerivaHandler: NoopHandler<GetConsultaDerivaRequest,
                                         GetConsultaDerivaResponse,
                                         InConAse270,
                                         In271ResDeriva>() {
    override fun getOutput(request: GetConsultaDerivaRequest): In271ResDeriva =
        In271ResDeriva().apply { this.idReceptor = request.coIafa }

    override fun createResponse(output: In271ResDeriva): GetConsultaDerivaResponse =
        GetConsultaDerivaResponse().apply {
            coError = ErrorCodes.TRANSACTION_UNUSED
            coIafa = output.idReceptor
            txNombre = Transactions.RES_271_RES_DERIVA
            txRespuesta = StringUtils.EMPTY
        }

    override fun createErrorResponse(errorCode: String, request: GetConsultaDerivaRequest): GetConsultaDerivaResponse =
        GetConsultaDerivaResponse().apply {
            coError = errorCode
            coIafa = request.coIafa
            txNombre = Transactions.RES_271_RES_DERIVA
            txRespuesta = StringUtils.EMPTY
        }
}
