package com.saludaunclic.semefa.siteds.service

import org.springframework.stereotype.Service

@Service
class NoopHandler: SitedsHandler<NoopHandler.NoopRequest, NoopHandler.NoopResponse> {
    override fun handleRequest(request: NoopRequest): NoopResponse {
        TODO("Not yet implemented")
    }

    override fun createResponse(request: NoopRequest, errorCode: String): NoopResponse {
        TODO("Not yet implemented")
    }

    class NoopRequest

    class NoopResponse
}