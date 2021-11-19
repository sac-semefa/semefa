package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.In997ResAut
import pe.gob.susalud.jr.transaccion.susalud.bean.InSolAut271
import pe.gob.susalud.jr.transaccion.susalud.service.In997ResAutService
import pe.gob.susalud.jr.transaccion.susalud.service.SolAut271Service
import pe.gob.susalud.ws.siteds.schemas.GetNumAutorizacionRequest
import pe.gob.susalud.ws.siteds.schemas.GetNumAutorizacionResponse

@Service
class NumAutorizacionHandler(private val sitedsValidator: SitedsValidator,
                             private val sitedsProperties: SitedsProperties,
                             private val solAut271Service: SolAut271Service,
                             private val in997ResAutService: In997ResAutService
): BaseSitedsHandler<GetNumAutorizacionRequest, GetNumAutorizacionResponse>() {
    companion object {
        const val PATH: String = "/numaut"
    }

    override fun handleRequest(request: GetNumAutorizacionRequest) {
        sitedsValidator.validate(request)

        val inSolAut271: InSolAut271 = solAut271Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inSolAut271)

        val in997ResAut: In997ResAut = sendBean(
            sitedsProperties.sacUrl + PATH,
            inSolAut271,
            In997ResAut::class.java)
        val x12: String = in997ResAutService.beanToX12N(in997ResAut)
        logConvertResponse(logger, in997ResAut, x12)
    }

    override fun createResponse(errorCode: String): GetNumAutorizacionResponse =
        GetNumAutorizacionResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = Transactions.RES_997_RES_AUT
        }
}
