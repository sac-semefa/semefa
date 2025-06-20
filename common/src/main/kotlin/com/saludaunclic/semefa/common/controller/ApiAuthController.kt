package com.saludaunclic.semefa.common.controller

import com.saludaunclic.semefa.common.dto.TokenDto
import com.saludaunclic.semefa.common.service.UserAuthenticationService
import com.saludaunclic.semefa.common.throwing.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/public/auth")
class ApiAuthController(val userAuthenticationService: UserAuthenticationService) {
    @PostMapping("/login", produces = [ MediaType.APPLICATION_JSON_VALUE ])
    fun login(
        @RequestParam("username") username: String,
        @RequestParam("password") password: String
    ): ResponseEntity<TokenDto> = ResponseEntity.ok(
        TokenDto(
            userAuthenticationService
                .login(username, password)
                .orElseThrow { ServiceException("Usuario o password inválido", HttpStatus.BAD_REQUEST) }))
}
