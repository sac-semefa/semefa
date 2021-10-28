package com.saludaunclic.semefa.regafi.repository

import com.saludaunclic.semefa.regafi.model.DataFrame
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface DataFrameRepository: CrudRepository<DataFrame, String> {
    fun findByMessageId(messageId: String): Optional<DataFrame>
    fun findByCorrelativeId(correlativeId: String): Optional<DataFrame>
}
