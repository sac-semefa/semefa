package com.saludaunclic.semefa.regafi.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import pe.gob.susalud.jr.transaccion.susalud.bean.In997RegafiUpdateExcepcion
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class SacIn997RegafiUpdateExcepcion(
    @JsonProperty("errorCampo") var errorCampo: String? = null,
    @JsonProperty("errorCampoRegla") var errorCampoRegla: String? = null
): In997RegafiUpdateExcepcion(), Serializable