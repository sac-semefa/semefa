package com.saludaunclic.semefa.siteds.interceptor

import org.apache.commons.lang3.StringUtils
import org.apache.cxf.binding.soap.Soap11
import org.apache.cxf.binding.soap.SoapBindingConstants
import org.apache.cxf.binding.soap.SoapMessage
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor
import org.apache.cxf.binding.soap.interceptor.SoapActionInInterceptor
import org.apache.cxf.helpers.CastUtils
import org.apache.cxf.message.Message
import org.apache.cxf.phase.Phase

class SoapActionResetInterceptor: AbstractSoapInterceptor(Phase.READ) {
    init {
        addBefore(SoapActionInInterceptor::class.java.name)
    }

    override fun handleMessage(message: SoapMessage) {
        if (message.version is Soap11) {
            val headers: MutableMap<String, List<String>>? = CastUtils.cast(message[Message.PROTOCOL_HEADERS] as Map<*, *>)
            if (headers != null) {
                val sa: List<String>? = headers[SoapBindingConstants.SOAP_ACTION]
                if (sa != null && sa.isNotEmpty()) {
                    headers[SoapBindingConstants.SOAP_ACTION] = mutableListOf(StringUtils.EMPTY)
                }
            }
        }
    }
}
