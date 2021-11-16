package com.saludaunclic.semefa.siteds.service

interface SitedsHandler<in Req: Any, out Res: Any> {
    fun handleRequest(request: Req)
    fun createResponse(errorCode: String): Res
}
