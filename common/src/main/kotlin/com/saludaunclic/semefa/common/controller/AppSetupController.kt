package com.saludaunclic.semefa.common.controller

import com.saludaunclic.semefa.common.dto.AppSetupDto
import com.saludaunclic.semefa.common.service.AppSetupService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/public/setup")
class AppSetupController(val appSetupService: AppSetupService) {
    @PostMapping(consumes = [ MediaType.APPLICATION_JSON_VALUE ], produces = [ MediaType.APPLICATION_JSON_VALUE ])
    fun setupApp(request: HttpServletRequest, @RequestBody appSetup: AppSetupDto): ResponseEntity<String> =
        ControllerHelper.whenFromLocalhost(request) { ResponseEntity.ok(appSetupService.setupApp(appSetup)) }
}
