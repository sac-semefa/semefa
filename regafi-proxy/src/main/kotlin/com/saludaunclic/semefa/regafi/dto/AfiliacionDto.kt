package com.saludaunclic.semefa.regafi.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
class AfiliacionDto(
    @JsonProperty("tiDocTitular") var tiDocTitular: String? = null,
    @JsonProperty("nuDocTitular") var nuDocTitular: String? = null,
    @JsonProperty("feNacimientoTitular") var feNacimientoTitular: String? = null,
    @JsonProperty("coPaisEmisorDocTitular") var coPaisEmisorDocTitular: String? = null,
    @JsonProperty("tiContratante") var tiContratante: String? = null,
    @JsonProperty("apPaternoContratante") var apPaternoContratante: String? = null,
    @JsonProperty("noContratante1") var noContratante1: String? = null,
    @JsonProperty("noContratante2") var noContratante2: String? = null,
    @JsonProperty("noContratante3") var noContratante3: String? = null,
    @JsonProperty("noContratante4") var noContratante4: String? = null,
    @JsonProperty("apMaternoContratante") var apMaternoContratante: String? = null,
    @JsonProperty("tiDocContratante") var tiDocContratante: String? = null,
    @JsonProperty("nuDocContratante") var nuDocContratante: String? = null,
    @JsonProperty("apCasadaContratante") var apCasadaContratante: String? = null,
    @JsonProperty("feNacimientoContratante") var feNacimientoContratante: String? = null,
    @JsonProperty("coPaisEmisorDocContratante") var coPaisEmisorDocContratante: String? = null,
    @JsonProperty("coAfiliacion") var coAfiliacion: String? = null,
    @JsonProperty("coContrato") var coContrato: String? = null,
    @JsonProperty("coUnicoMulisectorial") var coUnicoMulisectorial: String? = null,
    @JsonProperty("tiregimen") var tiregimen: String? = null,
    @JsonProperty("esAfiliacion") var esAfiliacion: String? = null,
    @JsonProperty("coCausalBaja") var coCausalBaja: String? = null,
    @JsonProperty("tiPlanSalud") var tiPlanSalud: String? = null,
    @JsonProperty("noProductoSalud") var noProductoSalud: String? = null,
    @JsonProperty("coProducto") var coProducto: String? = null,
    @JsonProperty("parentesco") var parentesco: String? = null,
    @JsonProperty("coRenipress") var coRenipress: String? = null,
    @JsonProperty("pkAfiliado") var pkAfiliado: String? = null,
    @JsonProperty("feActEstado") var feActEstado: String? = null,
    @JsonProperty("feIniAfiliacion") var feIniAfiliacion: String? = null,
    @JsonProperty("feFinAfiliacion") var feFinAfiliacion: String? = null,
    @JsonProperty("feIniCobertura") var feIniCobertura: String? = null,
    @JsonProperty("feFinCobertura") var feFinCobertura: String? = null,
    @JsonProperty("feActOperacion") var feActOperacion: String? = null,
    @JsonProperty("tiActOperacion") var tiActOperacion: String? = null,
    @JsonProperty("coTiCobertura") var coTiCobertura: String? = null,
    @JsonProperty("idAfiliacionNombre") var idAfiliacionNombre: String? = null
): Serializable
