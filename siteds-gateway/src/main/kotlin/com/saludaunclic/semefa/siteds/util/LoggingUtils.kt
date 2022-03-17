package com.saludaunclic.semefa.siteds.util

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger

object LoggingUtils {
    private val objectMapper: ObjectMapper = ObjectMapper()

    fun <T> logConvertRequest(logger: Logger, dataframe: String, bean: T) {
        val message = """Dataframe
                $dataframe
                converted to ${bean!!::class.java.name} bean
            """.trimMargin()
        if (logger.isDebugEnabled) {
            logger.debug(
                """$message:
                ${writePrettyJson(bean)}
                """.trimMargin()
            )
        } else {
            logger.info(message)
        }
    }

    fun <T> logConvertResponse(logger: Logger, bean: T, dataframe: String) {
        if (logger.isDebugEnabled) {
            logger.debug("""${bean!!::class.java.name} bean
                ${writePrettyJson(bean)}
                converted to dataframe:
                $dataframe
                """.trimMargin())
        } else {
            logger.info("""${bean!!::class.java.name} bean converted to dataframe:
                $dataframe
                """.trimMargin())
        }
    }

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
                """.trimMargin())
        } else {
            logger.info(message)
        }
    }

    private fun <T> writePrettyJson(bean: T): String = objectMapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(bean)
}
