package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants
import com.saludaunclic.semefa.siteds.throwing.SitedsException

interface SitedsHandler<in Req, out Any> {
    fun handleRequest(request: Req): Any
    fun createResponse(request: Req, errorCode: String): Any

    fun handle(request: Req): Any =
        try {
            handleRequest(request)
        } catch (ex: SitedsException) {
            createResponse(request, ex.errorCode)
        } catch (ex: Exception) {
            createResponse(request, SitedsConstants.ErrorCodes.SYSTEM_ERROR)
        }
}
