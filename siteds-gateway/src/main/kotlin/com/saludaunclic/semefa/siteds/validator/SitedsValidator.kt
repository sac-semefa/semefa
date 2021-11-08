package com.saludaunclic.semefa.siteds.validator

import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.SitedsConstants.ErrorCodes
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import com.saludaunclic.semefa.siteds.throwing.SitedsException
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Component
import pe.gob.susalud.ws.siteds.schemas.GetConsultaEntVinculadaRequest
import pe.gob.susalud.ws.siteds.schemas.GetConsultaRegAfiliadosRequest

@Component
class SitedsValidator(private val sitedsProperties: SitedsProperties) {
    fun validateConsultaEntVinculada(consultaEntVinculada: GetConsultaEntVinculadaRequest) {
        with(consultaEntVinculada) {
            whenFieldIsBlankThrow(txNombre, "Missing transaction name", ErrorCodes.TRANSACTION_NAME_MISSING)
            whenFieldIsNotExpected(
                txNombre,
                Transactions.REQ_278_CON_ENT_VINC,
                "Invalid transaction name",
                ErrorCodes.TRANSACTION_NAME_INVALID)
            whenFieldIsBlankThrow(coIafa, "Missing IAFA code", ErrorCodes.IAFA_CODE_MISSING)
        }
    }

    fun validateConsultaRegAfiliadosRequest(request: GetConsultaRegAfiliadosRequest) {
        /*if (!StringUtils.isBlank(request.txNombre)) {
            throw SitedsException("Nombre de transacción (txNombre) no está presente", HttpStatus.BAD_REQUEST)
        }
        if (!StringUtils.isBlank(request.coIafa)) {
            throw SitedsException("Código de IAFA (coIafa) no está presente", HttpStatus.BAD_REQUEST)
        }*/
    }

    private fun whenFieldIsBlankThrow(field: String, message: String, errorCode: String) {
        if (StringUtils.isBlank(field)) {
            throw SitedsException(message, errorCode)
        }
    }

    private fun whenFieldIsNotExpected(field: String, expected: String, message: String, errorCode: String) {
        if (field != expected) {
            throw SitedsException(message, errorCode)
        }
    }
}
