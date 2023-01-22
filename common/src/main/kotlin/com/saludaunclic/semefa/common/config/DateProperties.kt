package com.saludaunclic.semefa.common.config

import org.apache.commons.lang3.StringUtils
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "date")
class DateProperties(var timeZone: String = StringUtils.EMPTY)
