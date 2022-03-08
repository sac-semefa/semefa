package com.saludaunclic.semefa.util

import com.saludaunclic.semefa.regafi.dto.AppSetupDto
import com.saludaunclic.semefa.regafi.dto.UserDto
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import pe.gob.susalud.jr.transaccion.susalud.bean.In271RegafiUpdate
import pe.gob.susalud.jr.transaccion.susalud.bean.In271RegafiUpdateAfiliacion
import pe.gob.susalud.jr.transaccion.susalud.bean.In271RegafiUpdateAfiliado

class TestDataUtils {
    companion object {
        const val TEST_ADMIN_USER = "testAdmin"
        const val TEST_ADMIN_PASSWORD = "test123"

        fun generateAppSetup(username: String = TEST_ADMIN_USER, password: String = TEST_ADMIN_PASSWORD) =
            AppSetupDto(UserDto(username = username, password = password))

        fun loginWithCredentials(restTemplate: TestRestTemplate,
                                 username: String,
                                 password: String): ResponseEntity<String> =
            restTemplate.postForEntity(
                "/api/public/auth/login?username=$username&password=$password",
                null,
                String::class.java)

        fun generateIn271RegafiUpdate(noTransaccion: String = "271_REGAFI_UPDATE",
                                      idRemitente: String = "20001          ",
                                      idReceptor: String = "SUSALUD          ",
                                      feTransaccion: String = "19000130",
                                      hoTransaccion: String = "165224",
                                      idCorrelativo: String = "000000816",
                                      idTransaccion: String = "271",
                                      tiFinalidad: String = "13",
                                      caRemitente: String = "2",
                                      tiOperacion: String = "11"): In271RegafiUpdate =
            In271RegafiUpdate().apply {
                this.noTransaccion = noTransaccion
                this.idRemitente = idRemitente
                this.idReceptor = idReceptor
                this.feTransaccion = feTransaccion
                this.hoTransaccion = hoTransaccion
                this.idCorrelativo = idCorrelativo
                this.idTransaccion = idTransaccion
                this.tiFinalidad = tiFinalidad
                this.caRemitente = caRemitente
                this.tiOperacion = tiOperacion
                addDetalle(generateIn271Afiliado())
                addDetalle(generateIn271Afiliacion())
            }

        private fun generateIn271Afiliado(apPaternoAfiliado: String = "ACOSTA",
                                          noAfiliado1: String = "STEPHANNY MEDALIT",
                                          noAfiliado2: String = "",
                                          apMaternoAfiliado: String = "DAMIAN",
                                          tiDocumentoAfil: String = "7",
                                          nuDocumentoAfil: String = "12345678",
                                          estadoAfiliado: String = "1",
                                          tiDocumentoAct: String = "1",
                                          nuDocumentoAct: String = "45262782",
                                          apCasadaAfiliado: String = "",
                                          coNacionalidad: String = "PER",
                                          ubigeo: String = "140133",
                                          feNacimiento: String = "19000130",
                                          genero: String = "2",
                                          coPaisDoc: String = "ABC",
                                          fefallecimiento: String = "19000130",
                                          coPaisEmisorDocAct: String = "PER",
                                          feActPersonaxIafas: String = "20151202",
                                          idAfiliadoNombre: String = "1",
                                          tiDocTutor: String = "7",
                                          nuDocTutor: String = "12345678",
                                          tiVinculoTutor: String = "1",
                                          feValidacionReniec: String = "21001101",
                                          coPaisEmisorDocTutor: String = "ABC"): In271RegafiUpdateAfiliado =
            In271RegafiUpdateAfiliado().apply {
                this.apPaternoAfiliado = apPaternoAfiliado
                this.noAfiliado1 = noAfiliado1
                this.noAfiliado2 = noAfiliado2
                this.apMaternoAfiliado = apMaternoAfiliado
                this.tiDocumentoAfil = tiDocumentoAfil
                this.nuDocumentoAfil = nuDocumentoAfil
                this.estadoAfiliado = estadoAfiliado
                this.tiDocumentoAct = tiDocumentoAct
                this.nuDocumentoAct = nuDocumentoAct
                this.apCasadaAfiliado = apCasadaAfiliado
                this.coNacionalidad = coNacionalidad
                this.ubigeo = ubigeo
                this.feNacimiento = feNacimiento
                this.genero = genero
                this.coPaisDoc = coPaisDoc
                this.fefallecimiento = fefallecimiento
                this.coPaisEmisorDocAct = coPaisEmisorDocAct
                this.feActPersonaxIafas = feActPersonaxIafas
                this.idAfiliadoNombre = idAfiliadoNombre
                this.tiDocTutor = tiDocTutor
                this.nuDocTutor = nuDocTutor
                this.tiVinculoTutor = tiVinculoTutor
                this.feValidacionReniec = feValidacionReniec
                this.coPaisEmisorDocTutor = coPaisEmisorDocTutor
            }

        private fun generateIn271Afiliacion(tiDocTitular: String = "1",
                                            nuDocTitular: String = "20904254",
                                            feNacimientoTitular: String = "21001100",
                                            coPaisEmisorDocTitular: String = "PER",
                                            tiContratante: String = "1",
                                            apPaternoContratante: String = "",
                                            noContratante1: String = "NOMBRE CONTRATANTE",
                                            noContratante2: String = "",
                                            noContratante3: String = "",
                                            noContratante4: String = "",
                                            apMaternoContratante: String = "",
                                            tiDocContratante: String = "8",
                                            nuDocContratante: String = "20511421790",
                                            apCasadaContratante: String = "",
                                            feNacimientoContratante: String = "",
                                            coPaisEmisorDocContratante: String = "PER",
                                            coAfiliacion: String = "COD000000-",
                                            coContrato: String = "C000000",
                                            coUnicoMultisectorial: String = "",
                                            tiregimen: String = "2",
                                            esAfiliacion: String = "2",
                                            coCausalBaja: String = "1",
                                            tiPlanSalud: String = "2",
                                            noProductoSalud: String = "UNICO",
                                            coProducto: String = "P1",
                                            parentesco: String = "1",
                                            coRenipress: String = "",
                                            pkAfiliado: String = "PER120904254",
                                            feActEstado: String = "21001102",
                                            feIniAfiliacion: String = "21001101",
                                            feFinAfiliacion: String = "21001102",
                                            feIniCobertura: String = "21001104",
                                            feFinCobertura: String = "21001101",
                                            feActOperacion: String = "21001101",
                                            tiActOperacion: String = "202020",
                                            coTiCobertura: String = "1",
                                            idAfiliacionNombre: String = "111"): In271RegafiUpdateAfiliacion =
            In271RegafiUpdateAfiliacion().apply {
                this.tiDocTitular = tiDocTitular
                this.nuDocTitular = nuDocTitular
                this.feNacimientoTitular = feNacimientoTitular
                this.coPaisEmisorDocTitular = coPaisEmisorDocTitular
                this.tiContratante = tiContratante
                this.apPaternoContratante = apPaternoContratante
                this.noContratante1 = noContratante1
                this.noContratante2 = noContratante2
                this.noContratante3 = noContratante3
                this.noContratante4 = noContratante4
                this.apMaternoContratante = apMaternoContratante
                this.tiDocContratante = tiDocContratante
                this.nuDocContratante = nuDocContratante
                this.apCasadaContratante = apCasadaContratante
                this.feNacimientoContratante = feNacimientoContratante
                this.coPaisEmisorDocContratante = coPaisEmisorDocContratante
                this.coAfiliacion = coAfiliacion
                this.coContrato = coContrato
                this.coUnicoMultisectorial = coUnicoMultisectorial
                this.tiregimen = tiregimen
                this.esAfiliacion = esAfiliacion
                this.coCausalBaja = coCausalBaja
                this.tiPlanSalud = tiPlanSalud
                this.noProductoSalud = noProductoSalud
                this.coProducto = coProducto
                this.parentesco = parentesco
                this.coRenipress = coRenipress
                this.pkAfiliado = pkAfiliado
                this.feActEstado = feActEstado
                this.feIniAfiliacion = feIniAfiliacion
                this.feFinAfiliacion = feFinAfiliacion
                this.feIniCobertura = feIniCobertura
                this.feFinCobertura = feFinCobertura
                this.feActOperacion = feActOperacion
                this.tiActOperacion = tiActOperacion
                this.coTiCobertura = coTiCobertura
                this.idAfiliacionNombre = idAfiliacionNombre
            }
    }
}
