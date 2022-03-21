package com.saludaunclic.semefa.siteds.service

abstract class NoopHandler<in Req: Any, out Res: Any, In: Any, Out: Any>: SitedsHandler<Req, Res, In, Out> {
    override fun handle(request: Req): Res = createResponse(getOutput(request))

    abstract fun getOutput(request: Req): Out
}
