package com.saludaunclic.semefa.regafi.service.auth

import com.saludaunclic.semefa.regafi.model.User
import java.util.Optional

interface UserAuthenticationService {
    fun login(username: String, password: String): Optional<String>

    fun findByToken(token: String): Optional<User>

    fun logout(user: User)
}