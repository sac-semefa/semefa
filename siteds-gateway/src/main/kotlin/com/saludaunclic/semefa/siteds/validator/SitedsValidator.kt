package com.saludaunclic.semefa.siteds.validator

import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.SitedsConstants.ErrorCodes
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.throwing.SitedsException
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Component
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegCodRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaAsegNomRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaRegAfiliadosRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaSCTRRequest

@Component
class SitedsValidator(private val sitedsProperties: SitedsProperties) {
    fun validateConsultaEntVinculada(request: GetConsultaEntVinculadaRequest) {
        with(request) {
            basicValidation(txNombre, Transactions.REQ_278_CON_ENT_VINC, coIafa)
        }
    }

    fun validateConsultaAsegNom(request: GetConsultaAsegNomRequest) {
        with(request) {
            basicValidation(txNombre, Transactions.REQ_270_CON_ASE, coIafa)
        }
    }

    fun validateConsultaAsegCod(request: GetConsultaAsegCodRequest) {
        with(request) {
            basicValidation(txNombre, Transactions.REQ_270_CON_ASE, coIafa)
        }
    }

    fun validateConsultaAsegSctr(request: GetConsultaSCTRRequest) {
        with(request) {
            basicValidation(txNombre, Transactions.REQ_270_CON_ASE, coIafa)
        }
    }

    fun validateConsultaRegafi(request: GetConsultaRegAfiliadosRequest) {
        with(request) {
            basicValidation(txNombre, Transactions.REQ_270_REGAFI, coIafa)
        }
    }

    private fun basicValidation(txName: String?, expectedTxName: String,  iafaCode: String?) {
        whenFieldIsBlankThrow(txName, "Missing transaction name", ErrorCodes.TRANSACTION_NAME_MISSING)
        whenFieldIsNotExpected(
            txName,
            expectedTxName,
            "Invalid transaction name $txName, it should be: $expectedTxName",
            ErrorCodes.TRANSACTION_NAME_INVALID)
        whenFieldIsBlankThrow(iafaCode, "Missing IAFA code", ErrorCodes.IAFA_CODE_MISSING)
    }

    private fun whenFieldIsBlankThrow(field: String?, message: String, errorCode: String) {
        if (StringUtils.isBlank(field)) {
            throw SitedsException(message, errorCode)
        }
    }

    private fun whenFieldIsNotExpected(field: String?, expected: String, message: String, errorCode: String) {
        if (field != expected) {
            throw SitedsException(message, errorCode)
        }
    }
}
