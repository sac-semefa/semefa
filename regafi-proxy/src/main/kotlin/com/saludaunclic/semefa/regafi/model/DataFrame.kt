package com.saludaunclic.semefa.regafi.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.sql.Timestamp

@Table("data_frame")
data class DataFrame(
    @Id var id: Long? = null,
    @Column var messageId: String? = null,
    @Column var correlativeId: String? = null,
    @Column var ack: String? = null,
    @Column var type: DataFrameType? = null,
    @Column var status: DataFrameStatus? = null,
    @Column var processDate: Timestamp? = null,
    @Column var attempts: Int = 0,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DataFrame

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}