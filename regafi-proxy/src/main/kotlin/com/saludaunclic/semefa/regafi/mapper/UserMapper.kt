package com.saludaunclic.semefa.regafi.mapper

import com.saludaunclic.semefa.regafi.dto.RoleDto
import com.saludaunclic.semefa.regafi.dto.UserDto
import com.saludaunclic.semefa.regafi.model.Role
import com.saludaunclic.semefa.regafi.model.User
import org.springframework.stereotype.Component

@Component
class UserMapper {
    fun toModel(role: RoleDto): Role = Role(role.id, role.name)

    fun fromModel(role: Role): RoleDto = RoleDto(role.id, role.name)

    fun toModel(user: UserDto): User =
        with(user) {
            User(
                id = id,
                username = username,
                password = password,
                encrypted = encrypted,
                status = status,
                roles = roles.map { toModel(it) }.toSet()
            )
        }

    fun fromModel(user: User): UserDto =
        with(user) {
            UserDto(
                id = id,
                username = username,
                password = password,
                encrypted = encrypted,
                status = status,
                roles = roles.map { fromModel(it) }.toSet()
            )
        }
}