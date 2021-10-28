package com.saludaunclic.semefa.regafi.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class AfiliadoDto(
    @JsonProperty("apPaternoAfiliado") var apPaternoAfiliado: String? = null,
    @JsonProperty("noAfiliado1") var noAfiliado1: String? = null,
    @JsonProperty("noAfiliado2") var noAfiliado2: String? = null,
    @JsonProperty("apMaternoAfiliado") var apMaternoAfiliado: String? = null,
    @JsonProperty("tiDocumentoAfil") var tiDocumentoAfil: String? = null,
    @JsonProperty("nuDocumentoAfil") var nuDocumentoAfil: String? = null,
    @JsonProperty("estadoAfiliado") var estadoAfiliado: String? = null,
    @JsonProperty("tiDocumentoAct") var tiDocumentoAct: String? = null,
    @JsonProperty("nuDocumentoAct") var nuDocumentoAct: String? = null,
    @JsonProperty("apCasadaAfiliado") var apCasadaAfiliado: String? = null,
    @JsonProperty("coNacionalidad") var coNacionalidad: String? = null,
    @JsonProperty("ubigeo") var ubigeo: String? = null,
    @JsonProperty("feNacimiento") var feNacimiento: String? = null,
    @JsonProperty("genero") var genero: String? = null,
    @JsonProperty("coPaisDoc") var coPaisDoc: String? = null,
    @JsonProperty("fefallecimiento") var fefallecimiento: String? = null,
    @JsonProperty("coPaisEmisorDocAct") var coPaisEmisorDocAct: String? = null,
    @JsonProperty("feActPersonaxIafas") var feActPersonaxIafas: String? = null,
    @JsonProperty("idAfiliadoNombre") var idAfiliadoNombre: String? = null,
    @JsonProperty("tiDocTutor") var tiDocTutor: String? = null,
    @JsonProperty("nuDocTutor") var nuDocTutor: String? = null,
    @JsonProperty("tiVinculoTutor") var tiVinculoTutor: String? = null,
    @JsonProperty("feValidacionReniec") var feValidacionReniec: String? = null,
    @JsonProperty("coPaisEmisorDocTutor") var coPaisEmisorDocTutor: String? = null
): Serializable
