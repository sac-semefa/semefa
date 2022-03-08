package com.saludaunclic.semefa.regafi.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotNull
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
data class AppSetupDto(
    @NotNull(message = "Usuario debe incluirse")
    @JsonProperty("user")
    var user: UserDto
): Serializable {
    override fun toString(): String {
        return "AppSetupDto(user=$user)"
    }
}
