package com.saludaunclic.semefa.common.repository

import com.saludaunclic.semefa.common.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository: CrudRepository<User, Int> {
    fun findByUsername(username: String): Optional<User>
}
