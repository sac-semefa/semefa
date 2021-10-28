package com.saludaunclic.semefa.regafi.mapper

import com.saludaunclic.semefa.regafi.dto.RoleDto
import com.saludaunclic.semefa.regafi.dto.SacIn997RegafiUpdate
import com.saludaunclic.semefa.regafi.dto.SacIn997RegafiUpdateExcepcion
import com.saludaunclic.semefa.regafi.dto.UserDto
import com.saludaunclic.semefa.regafi.model.Role
import com.saludaunclic.semefa.regafi.model.User
import org.springframework.stereotype.Component
import pe.gob.susalud.jr.transaccion.susalud.bean.In997RegafiUpdate
import pe.gob.susalud.jr.transaccion.susalud.bean.In997RegafiUpdateExcepcion

@Component
class RegoofyMapper {
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

    fun toSac997UpdateException(exception: In997RegafiUpdateExcepcion): SacIn997RegafiUpdateExcepcion =
        SacIn997RegafiUpdateExcepcion().apply {
            excBD = exception.excBD
            coCampoErr = exception.coCampoErr
            inCoErrorEncontrado = exception.inCoErrorEncontrado
            pkAfiliado = exception.pkAfiliado
            pkAfiliadopkAfiliacion = exception.pkAfiliadopkAfiliacion
        }

    fun toSac997Update(messageId: String, update: In997RegafiUpdate): SacIn997RegafiUpdate =
        SacIn997RegafiUpdate(
            messageId,
            update.in271RegafiUpdateExcepcion.map { toSac997UpdateException(it) })
            .apply {
                noTransaccion = update.noTransaccion
                idRemitente = update.idRemitente
                idReceptor = update.idReceptor
                feTransaccion = update.feTransaccion
                hoTransaccion = update.hoTransaccion
                idCorrelativo = update.idCorrelativo
                idTransaccion = update.idTransaccion
                excProceso = update.excProceso
                nuControl = update.nuControl
                nuControlST = update.nuControlST
            }
}