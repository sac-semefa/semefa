package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.SitedsConstants.ErrorCodes
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.throwing.SitedsException
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertRequest
import com.saludaunclic.semefa.siteds.util.LoggingUtils.logConvertResponse
import com.saludaunclic.semefa.siteds.validator.SitedsValidator
import org.springframework.stereotype.Service
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.bean.InConCod271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConNom271
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConCod271Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConNom271Service
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomResponse

@Service
class ConsultaAsegNomHandler(private val sitedsValidator: SitedsValidator,
                             private val sitedsProperties: SitedsProperties,
                             private val conAse270Service: ConAse270Service,
                             private val conNom271Service: ConNom271Service,
                             private val conCod271Service: ConCod271Service)
    : BaseSitedsHandler<GetConsultaAsegNomRequest, GetConsultaAsegNomResponse>() {
    override fun handleRequest(request: GetConsultaAsegNomRequest) {
        sitedsValidator.validateConsultaAsegNom(request)

        val inConAse270: InConAse270 = conAse270Service.x12NToBean(request.txPeticion)
        logConvertRequest(logger, request.txPeticion, inConAse270)

        val x12: String
        val bean: Any
        when(inConAse270.txRequest) {
            "CN" -> {
                bean = sendBean(
                    sitedsProperties.sacUrl + "/conase",
                    inConAse270,
                    InConNom271::class.java)
                x12 = conNom271Service.beanToX12N(bean)
            }
            "CC" -> {
                bean = sendBean(
                    sitedsProperties.sacUrl + "/conase",
                    inConAse270,
                    InConCod271::class.java)
                x12 = conCod271Service.beanToX12N(bean)
            }
            else -> throw SitedsException(
                "Invalid TX Request (Identification Reference)",
                ErrorCodes.ID_REF_INVALID)
        }
        logConvertResponse(logger, bean, x12)
    }

    override fun createResponse(errorCode: String, txName: String): GetConsultaAsegNomResponse =
        GetConsultaAsegNomResponse().apply {
            coError = errorCode
            coIafa = sitedsProperties.iafaCode
            txNombre = txName
        }

    override fun resolveTxName(request: GetConsultaAsegNomRequest): String =
        with(request) {
            val inConAse270: InConAse270 = conAse270Service.x12NToBean(request.txPeticion)
            when (inConAse270.txRequest) {
                "CN" -> SitedsConstants.Transactions.RES_271_CON_NOM
                "CC" -> SitedsConstants.Transactions.RES_271_CON_COD
                else -> throw SitedsException(
                    "Invalid TX Request (Identification Reference)",
                    ErrorCodes.ID_REF_INVALID
                )
            }
        }
}
