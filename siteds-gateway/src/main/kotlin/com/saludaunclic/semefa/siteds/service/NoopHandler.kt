package com.saludaunclic.semefa.siteds.service

import org.springframework.stereotype.Service

@Service
class NoopHandler: BaseSitedsHandler<NoopHandler.NoopRequest, NoopHandler.NoopResponse>() {
    override fun resolveTxName(request: NoopRequest): String {
        TODO("Not yet implemented")
    }

    override fun handleRequest(request: NoopRequest) {
        TODO("Not yet implemented")
    }

    override fun createResponse(errorCode: String, txName: String): NoopResponse {
        TODO("Not yet implemented")
    }

    class NoopRequest

    class NoopResponse
}