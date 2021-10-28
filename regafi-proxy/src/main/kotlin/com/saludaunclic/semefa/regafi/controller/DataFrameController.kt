package com.saludaunclic.semefa.regafi.controller

import com.saludaunclic.semefa.regafi.dto.SacIn997RegafiUpdate
import com.saludaunclic.semefa.regafi.service.sac.RegafiService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pe.gob.susalud.jr.transaccion.susalud.bean.In271RegafiUpdate

@RestController
@RequestMapping("/api/regafi")
class DataFrameController(val regafiService: RegafiService) {
    @RequestMapping("/update271")
    @PostMapping(consumes = [ MediaType.APPLICATION_JSON_VALUE ], produces = [ MediaType.APPLICATION_JSON_VALUE ])
    fun update271(@RequestBody in271RegafiUpdate: In271RegafiUpdate): ResponseEntity<SacIn997RegafiUpdate> =
        ResponseEntity.ok(regafiService.process271DataFrame(in271RegafiUpdate))

    @RequestMapping("/get271/{messageId}")
    @GetMapping(produces = [ MediaType.APPLICATION_JSON_VALUE ])
    fun get271(@PathVariable("messageId") messageId: String): ResponseEntity<SacIn997RegafiUpdate> =
        ResponseEntity.ok(regafiService.get271DataFrame(messageId))
}