package com.saludaunclic.semefa.common.util

import org.slf4j.Logger

object SemefaUtils {
    const val NO_ERROR: String = "0000"
    const val ERROR_CODE_ELEMENT = "coError"
    const val TX_RESPONSE_ELEMENT: String = "txRespuesta"

    fun extractElement(logger: Logger, xmlText: String, element: String): String {
        logger.debug("Extracting X12 from $xmlText with tag $element")
        val split = xmlText.split(element)
        val second = split[if (split.size > 1) 1 else 0]
        return second
            .substring(1, second.length - 2)
            .also { logger.debug("X12 extracted: $this") }
    }
}
