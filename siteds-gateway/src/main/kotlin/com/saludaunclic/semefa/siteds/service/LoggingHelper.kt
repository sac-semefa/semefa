package com.saludaunclic.semefa.siteds.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.io.StringWriter
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

@Service
class LoggingHelper(private val sitedsProperties: SitedsProperties) {
    private val marshallers = mutableMapOf<String, Marshaller>()
    private val objectMapper: ObjectMapper = ObjectMapper()

    fun <T> logSendJson(logger: Logger, bean: T) {
        logBeanAction(logger, "SENDING to SAC", bean) { jsonContent(bean) }
    }

    fun <T> logReceiveJson(logger: Logger, bean: T) {
        logBeanAction(logger, "RECEIVING from SAC", bean) { jsonContent(bean) }
    }

    fun <T: Any> logSendXml(logger: Logger, bean: T) {
        logBeanAction(logger, "SENDING to IPRESS", bean) { xmlContent(bean) }
    }

    fun <T: Any> logReceiveXml(logger: Logger, bean: T) {
        logBeanAction(logger, "RECEIVING from IPRESS", bean) { xmlContent(bean) }
    }

    private fun <T> logBeanAction(logger: Logger, prefix: String, bean: T, transformer: (T) -> String) {
        if (!logger.isDebugEnabled) {
            return
        }

        val start = "$prefix ${bean!!::class.java.name} bean"
        val message = transformer.invoke(bean)
        logger.debug("""$start:
            $message
""".trimStart())
    }

    private fun <T> writePrettyJson(bean: T): String = objectMapper
        .writerWithDefaultPrettyPrinter()
        .writeValueAsString(bean)

    private fun <T> jsonContent(bean: T): String =
        if (sitedsProperties.isDevMode()) writePrettyJson(bean)
        else objectMapper.writeValueAsString(bean)

    private fun resolveMarshaller(bean: Any): Marshaller? = with(bean) {
        val name: String = this.javaClass.name
        if (!marshallers.containsKey(name)) {
            marshallers[name] = JAXBContext.newInstance(this.javaClass).createMarshaller()
        }

        return marshallers[name]
    }

    private fun <T: Any> xmlContent(bean: T): String = with(bean) {
        val marshaller = resolveMarshaller(bean)
        val writer = StringWriter()
        marshaller!!.marshal(bean, writer)
        writer.toString()
    }
}