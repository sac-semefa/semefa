package com.saludaunclic.semefa.siteds.service

interface SitedsHandler<in Req: Any, out Res: Any, In: Any, Out: Any> {
    fun handle(request: Req): Res
    fun createResponse(output: Out): Res
    fun createErrorResponse(errorCode: String, request: Req): Res
}
