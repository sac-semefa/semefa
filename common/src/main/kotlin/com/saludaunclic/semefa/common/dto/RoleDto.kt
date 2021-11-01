package com.saludaunclic.semefa.common.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Size
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class RoleDto(
    var id: Int? = null,
    @Size(min = 4, max = 16, message = "Role debe tener al menos 4 y como máximo 16 caracteres")
    @JsonProperty("name")
    var name: String
): Serializable
