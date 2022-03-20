package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.SitedsConstants

interface SitedsHandler2<in Req: Any, out Res: Any, In: Any, Out: Any> {
    fun handle(request: Req): Res
//    fun handleRequest(request: Req): Out
    fun createResponse(output: Out): Res
    fun createErrorResponse(errorCode: String, request: Req): Res
}
