package com.saludaunclic.semefa.siteds.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.saludaunclic.semefa.siteds.config.SitedsProperties
import org.apache.commons.lang3.StringUtils
import org.slf4j.Logger
import org.springframework.stereotype.Service
import java.io.StringWriter
import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

@Service
class LoggingHelper(private val sitedsProperties: SitedsProperties) {
    private val marshallers = mutableMapOf<String, Marshaller>()
    private val objectMapper: ObjectMapper = ObjectMapper()

    fun <T> logSendJson(logger: Logger, bean: T, url: String) {
        logBeanAction(logger, "SENDING to SAC${includeUrl(url)}", bean) { jsonContent(bean) }
    }

    fun <T> logReceiveJson(logger: Logger, bean: T, url: String) {
        logBeanAction(logger, "RECEIVING from SAC${includeUrl(url)}", bean) { jsonContent(bean) }
    }

    fun <T: Any> logSendXml(logger: Logger, bean: T) {
        logBeanAction(logger, "SENDING to IPRESS", bean) { xmlContent(bean) }
    }

    fun <T: Any> logReceiveXml(logger: Logger, bean: T) {
        logBeanAction(logger, "RECEIVING from IPRESS", bean) { xmlContent(bean) }
    }

    private fun includeUrl(url: String = StringUtils.EMPTY): String =
        if (StringUtils.isNoneBlank(url)) " ($url)"
        else StringUtils.EMPTY

    private fun <T> logBeanAction(logger: Logger, prefix: String, bean: T, transformer: (T) -> String) {
        if (!logger.isDebugEnabled) {
            return
        }

        val start = "$prefix bean [${bean!!::class.java.name}]:"
        logger.debug("""$start:
            ${transformer.invoke(bean)}
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
            marshallers[name] = JAXBContext.newInstance(this.javaClass).createMarshaller().apply {
                if (sitedsProperties.isDevMode()) {
                    this.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
                }
            }
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