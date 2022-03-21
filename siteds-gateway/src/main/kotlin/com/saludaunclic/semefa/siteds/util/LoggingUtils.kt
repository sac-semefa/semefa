package com.saludaunclic.semefa.siteds.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger

object LoggingUtils {
    private val objectMapper: ObjectMapper = ObjectMapper()

    fun <T> logSend(logger: Logger, bean: T) {
        logBeanAction(logger, "SENDING to SAC", bean)
    }

    fun <T> logReceive(logger: Logger, bean: T) {
        logBeanAction(logger, "RECEIVING from SAC", bean)
    }

    private fun <T> logBeanAction(logger: Logger, prefix: String, bean: T) {
        val message = "$prefix ${bean!!::class.java.name} bean"
        if (logger.isDebugEnabled) {
            logger.debug("""$message :
${writePrettyJson(bean)}
""")
        } else {
            logger.info(message)
        }
    }

    private fun <T> writePrettyJson(bean: T): String = objectMapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(bean)
}
