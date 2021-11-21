package com.saludaunclic.semefa.siteds.service

import org.apache.commons.lang3.StringUtils

abstract class StringOutputSitedsHandler<in Req: Any, out Res: Any>: BaseSitedsHandler<Req, Res, String>() {
    override fun errorOutput(): String = StringUtils.EMPTY
}
