package com.saludaunclic.semefa.siteds.service

interface SitedsHandler<in Req: Any, out Res: Any, Out: Any> {
    fun handleRequest(request: Req): Out
    fun createResponse(errorCode: String, output: Out): Res
}
