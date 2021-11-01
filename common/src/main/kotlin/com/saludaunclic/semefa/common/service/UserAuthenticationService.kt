package com.saludaunclic.semefa.common.service

import com.saludaunclic.semefa.common.model.User
import java.util.Optional

interface UserAuthenticationService {
    fun login(username: String, password: String): Optional<String>

    fun findByToken(token: String): Optional<User>

    fun logout(user: User)
}