package com.saludaunclic.semefa.regafi.mapper

import com.saludaunclic.semefa.regafi.dto.SacIn997RegafiUpdate
import com.saludaunclic.semefa.regafi.dto.SacIn997RegafiUpdateExcepcion
import org.springframework.stereotype.Component
import pe.gob.susalud.jr.transaccion.susalud.bean.In997RegafiUpdate
import pe.gob.susalud.jr.transaccion.susalud.bean.In997RegafiUpdateExcepcion

@Component
class RegafyMapper {
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