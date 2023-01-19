package com.saludaunclic.semefa.siteds.controller

import com.saludaunclic.semefa.siteds.model.MqAckResponse
import com.saludaunclic.semefa.siteds.service.AccreditacionHandler
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pe.gob.susalud.jr.transaccion.susalud.bean.InLogAcreInsert271

@RestController
@RequestMapping("/api/logacre")
class AccreditationController(val accreditacionHandler: AccreditacionHandler) {
    @PostMapping(consumes = [ MediaType.APPLICATION_JSON_VALUE ], produces = [ MediaType.APPLICATION_JSON_VALUE ])
    fun logAccreditation(@RequestBody inLogAcreInsert271: InLogAcreInsert271): ResponseEntity<MqAckResponse> =
        ResponseEntity.ok(accreditacionHandler.processAccreditation(inLogAcreInsert271))
}