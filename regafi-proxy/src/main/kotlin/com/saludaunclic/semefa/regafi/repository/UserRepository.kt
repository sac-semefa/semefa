package com.saludaunclic.semefa.regafi.repository

import com.saludaunclic.semefa.regafi.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: CrudRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>
}
