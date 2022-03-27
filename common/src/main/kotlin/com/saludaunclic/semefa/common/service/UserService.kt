package com.saludaunclic.semefa.common.service

import com.saludaunclic.semefa.common.model.User
import java.util.Optional

interface UserService {
    fun save(user: User): User
    fun find(id: Int): Optional<User>
    fun findByUsername(username: String): Optional<User>
    fun userCount(): Long
    fun passwordMatches(provided: String, current: String): Boolean
}
