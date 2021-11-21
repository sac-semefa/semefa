package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.ErrorCodes
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDerivaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaDerivaResponse

@Service
class ConsultaDerivaHandler(private val sitedsValidator: SitedsValidator,
                            private val sitedsProperties: SitedsProperties)
    : StringOutputSitedsHandler<GetConsultaDerivaRequest, GetConsultaDerivaResponse>() {
    override fun handleRequest(request: GetConsultaDerivaRequest): String {
        sitedsValidator.validate(request)
        return StringUtils.EMPTY
    }

    override fun createResponse(errorCode: String, output: String): GetConsultaDerivaResponse =
        GetConsultaDerivaResponse().apply {
            coError = ErrorCodes.TRANSACTION_UNUSED
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_271_RES_DERIVA
            txRespuesta = output
        }
}
