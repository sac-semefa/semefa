package com.saludaunclic.semefa.siteds.validator

import com.saludaunclic.semefa.siteds.config.SitedsProperties
import com.saludaunclic.semefa.siteds.throwing.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import pe.gob.susalud.ws.siteds.schemas.GetConsultaRegAfiliadosRequest

@Component
class SitedsValidator(private val sitedsProperties: SitedsProperties) {
    fun validateConsultaRegAfiliadosRequest(request: GetConsultaRegAfiliadosRequest) {
        if (!StringUtils.hasText(request.txNombre)) {
            throw ServiceException("Nombre de transacción (txNombre) no está presente", HttpStatus.BAD_REQUEST)
        }
        if (!StringUtils.hasText(request.coIafa)) {
            throw ServiceException("Código de IAFA (coIafa) no está presente", HttpStatus.BAD_REQUEST)
        }
    }
}
