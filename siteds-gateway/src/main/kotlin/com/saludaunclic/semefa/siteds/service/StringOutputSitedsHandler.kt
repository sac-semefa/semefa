package com.saludaunclic.semefa.siteds.service

import com.saludaunclic.semefa.siteds.config.SitedsProperties
import org.apache.commons.lang3.StringUtils

abstract class StringOutputSitedsHandler<in Req: Any, out Res: Any>(sitedsProperties: SitedsProperties)
    : BaseSitedsHandler<Req, Res, String>(sitedsProperties) {
    override fun errorOutput(): String = StringUtils.EMPTY
}
