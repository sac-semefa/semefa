package com.saludaunclic.semefa.siteds.config

import com.saludaunclic.semefa.siteds.endpoint.SitedsEndpoint
import com.saludaunclic.semefa.siteds.interceptor.SoapActionResetInterceptor
import org.apache.cxf.Bus
import org.apache.cxf.jaxws.EndpointImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.xml.ws.Endpoint

@Configuration
class CxfConfig(val bus: Bus) {
    @Bean fun endpoint(sitedsEndpoint: SitedsEndpoint): Endpoint =
        EndpointImpl(bus, sitedsEndpoint).apply {
            inInterceptors.add(SoapActionResetInterceptor())
            publish("/SitedsService")
        }
}
