package com.saludaunclic.semefa.regafi.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import org.springframework.security.core.GrantedAuthority

@Table("app_role")
data class Role(
    @Id var id: Int? = null,
    @Column var name: String
): GrantedAuthority {
    companion object {
        const val SUPER_ROLE = "SUPER"
    }
    override fun getAuthority(): String = name

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Role

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int = id ?: 0

    override fun toString(): String = "Role(id=$id, name='$name')"
}
