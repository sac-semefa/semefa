package com.saludaunclic.semefa.regafi.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("field_error_rule")
data class FieldErrorRule(
    @Id var id: Int,
    @Column var description: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FieldErrorRule

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
