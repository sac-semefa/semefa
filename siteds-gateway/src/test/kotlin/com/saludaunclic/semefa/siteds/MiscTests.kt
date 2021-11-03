package com.saludaunclic.semefa.siteds

import com.fasterxml.jackson.databind.ObjectMapper
import com.saludaunclic.semefa.common.service.DefaultDateService
import com.saludaunclic.semefa.common.util.SemefaUtils
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.Test
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.bean.InConCod271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConCod271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InConEntVinc278
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConAse270ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConCod271ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConEntVinc278ServiceImpl
import java.util.TimeZone
import java.util.UUID

class MiscTests {
    companion object {
        const val IAFA_CODE = "30009"
        val lineSep: String = System.lineSeparator()
    }

    private val dateService = DefaultDateService(TimeZone.getTimeZone("UTC-5"))
    private val objectMapper = ObjectMapper()
    private val printSoap = false
    private val printJson = true

    @Test
    fun conEntVinc278_test() {
        val conEntVinc278Service = ConEntVinc278ServiceImpl()
        val inConEntVinc278: InConEntVinc278 = generarInSolEntVinc278()
        val name = "InConEntVinc278 - Entidad vinculada"
        printSoap(name) { conEntVinc278Service.beanToX12N(inConEntVinc278) }
        printJson(name) {  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(inConEntVinc278) }
    }

    @Test
    fun inConAse270_CN_test() {
        val conAse270Service = ConAse270ServiceImpl()
        val inConAse270: InConAse270 = generarConAse270("CN")
        val name = "InConAse270 - Consulta por Nombre o por DNI"
        printSoap(name) { conAse270Service.beanToX12N(inConAse270) }
        printJson(name) { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(inConAse270) }
    }

    @Test
    fun inConAse270_CC_test() {
        val conAse270Service = ConAse270ServiceImpl()
        val inConAse270: InConAse270 = generarConAse270("CC")
        val name = "InConAse270 - Consulta por Código de Asegurado"
        printSoap(name) { conAse270Service.beanToX12N(inConAse270) }
        printJson(name) { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(inConAse270) }
    }

    @Test
    fun inConCod271_test() {
        val conCod271Service = ConCod271ServiceImpl()
        val inConCod271: InConCod271 = generarConCod271()
        val name = "InConCod271 - Consulta por Código de Asegurado"
        printSoap(name) { conCod271Service.beanToX12N(inConCod271) }
        printJson(name) { objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(inConCod271) }
    }

    private fun printSeparator(name: String, sep: String = "#") {
        val repeated = StringUtils.repeat(sep, 24)
        println("$lineSep $repeated $name $repeated $lineSep")
    }

    private fun printSoap(name: String, data: () -> String) {
        if (printSoap) {
            printSeparator("SOAP request: $name")
            println(data.invoke())
        }
    }

    private fun printJson(name: String, data: () -> String) {
        if (printJson) {
            printSeparator("SAC JSON: $name")
            println(data.invoke())
        }
    }

    private fun generarInSolEntVinc278(): InConEntVinc278 {
        val inSolEntVinc278 = InConEntVinc278()
        inSolEntVinc278.noTransaccion = SitedsConstants.Transactions.REQ_278_CON_ENT_VINC
        inSolEntVinc278.idRemitente = SemefaUtils.fillWithSpaces("98001C")
        inSolEntVinc278.idReceptor = SemefaUtils.fillWithSpaces(IAFA_CODE)
        val now = dateService.now()
        inSolEntVinc278.feTransaccion = dateService.formatDate(now)
        inSolEntVinc278.hoTransaccion = dateService.formatTime(now)
        inSolEntVinc278.idCorrelativo = UUID.randomUUID().toString()
        inSolEntVinc278.idTransaccion = "278"
        inSolEntVinc278.tiFinalidad = "13"
        inSolEntVinc278.caIPRESS = "2"
        inSolEntVinc278.noIPRESS = "SEMEFA"
        inSolEntVinc278.tiDoIPRESS = "6"
        inSolEntVinc278.nuRucIPRESS = "20100121809"
        return inSolEntVinc278
    }

    private fun generarConAse270(txRequest: String): InConAse270 {
        val inConAse270 = InConAse270()
        inConAse270.noTransaccion = SitedsConstants.Transactions.REQ_270_CON_ASE
        inConAse270.idRemitente = "980001C"
        inConAse270.idReceptor = SemefaUtils.fillWithSpaces(IAFA_CODE)
        val now = dateService.now()
        inConAse270.feTransaccion = dateService.formatDate(now)
        inConAse270.hoTransaccion = dateService.formatTime(now)
        inConAse270.idCorrelativo = UUID.randomUUID().toString()
        inConAse270.idTransaccion = "270"
        inConAse270.tiFinalidad = "13"
        inConAse270.caRemitente = "2"
        inConAse270.nuRucRemitente = "20100054184"
        inConAse270.txRequest = txRequest
        inConAse270.caReceptor = "2"
        inConAse270.caPaciente = "1"
        inConAse270.apPaternoPaciente = "rengifo"
        inConAse270.noPaciente = "JOSE"
        inConAse270.coAfPaciente = "0000062408"
        inConAse270.apMaternoPaciente = "GUTIEREZ"
        inConAse270.tiDocumento = "1"
        inConAse270.nuDocumento = "08834001"
        inConAse270.coProducto = "4"
        inConAse270.deProducto = "Descripción "
        inConAse270.coInProducto = "001"
        inConAse270.nuCobertura = "4"
        inConAse270.deCobertura = "2"
        inConAse270.caServicio = "1"
        inConAse270.coCalservicio = "Z3"
        inConAse270.beMaxInicial = "50.9"
        inConAse270.coTiCobertura = "4"
        inConAse270.coSuTiCobertura = "001"
        inConAse270.coAplicativoTx = "1"
        inConAse270.coEspecialidad = "CO321"
        inConAse270.coParentesco = "01"
        inConAse270.nuPlan = "00743621"
        inConAse270.nuAutOrigen = "0011223344"
        inConAse270.tiAccidente = "1"
        inConAse270.feAccidente = "20161511"
        inConAse270.tiCaContratante = "2"
        inConAse270.noPaContratante = "ICM PACHAPAQUI SAC"
        inConAse270.noContratante = "ICM PACHAPAQUI SAC"
        inConAse270.noMaContratante = "ICM PACHAPAQUI SAC"
        inConAse270.tiDoContratante = "6"
        inConAse270.idReContratante = "XX5"
        inConAse270.coReContratante = "20100973473"
        return inConAse270
    }

    private fun generarConCod271(): InConCod271 {
        val inConCod271 = InConCod271()
        inConCod271.noTransaccion = "271_CON_COD"
        inConCod271.idRemitente = "20002          "
        inConCod271.idReceptor = "980001C        "
        inConCod271.feTransaccion = "20150311"
        inConCod271.hoTransaccion = "152200"
        inConCod271.idCorrelativo = "756496608"
        inConCod271.idTransaccion = "271"
        inConCod271.tiFinalidad = "11"
        inConCod271.caRemitente = "2"
        inConCod271.userRemitente = "USER IAFAS"
        inConCod271.passRemitente = "PASS IAFAS"
        inConCod271.feUpFoto = "20150607"
        inConCod271.caReceptor = "2"
        inConCod271.nuRucReceptor = "20100054184"
        inConCod271.caPaciente = "1"
        inConCod271.apPaternoPaciente = "RUIZ"
        inConCod271.noPaciente = "ERNESTO ANGEL"
        inConCod271.coAfPaciente = "0000424422"
        inConCod271.apMaternoPaciente = "PEREZ"
        inConCod271.coEsPaciente = "1"
        inConCod271.tiDoPaciente = "1"
        inConCod271.nuDoPaciente = "08834001"
        inConCod271.nuIdenPaciente = "00001"
        inConCod271.nuContratoPaciente = "131313"
        inConCod271.nuPoliza = "3134124314"
        inConCod271.nuCertificado = "645318424"
        inConCod271.coTiPoliza = "1"
        inConCod271.coProducto = "EPS"
        inConCod271.deProducto = "Descripcion"
        inConCod271.nuPlan = "00743621"
        inConCod271.tiPlanSalud = "7"
        inConCod271.coMoneda = "1"
        inConCod271.coParentesco = "01"
        inConCod271.soBeneficio = "S"
        inConCod271.nuSoBeneficio = "01"
        inConCod271.feNacimiento = "19900512"
        inConCod271.genero = "1"
        inConCod271.esMarital = "2"
        inConCod271.feIniVigencia = "20040701"
        inConCod271.feFinVigencia = "20040801"
        inConCod271.tiCaContratante = "2"
        inConCod271.noPaContratante = "ICM PACHAPAQUI SAC"
        inConCod271.noContratante = "ICM PACHAPAQUI SAC"
        inConCod271.noMaContratante = "ICM PACHAPAQUI SAC"
        inConCod271.tiDoContratante = "6"
        inConCod271.idReContratante = "XX5"
        inConCod271.coReContratante = "20100973473"
        inConCod271.caTitular = "1"
        inConCod271.noPaTitular = "MENDOZA"
        inConCod271.noTitular = "LUIS"
        inConCod271.coAfTitular = "0000424421"
        inConCod271.noMaTitular = "ARIAS"
        inConCod271.tiDoTitular = "1"
        inConCod271.nuDoTitular = "09834991"
        inConCod271.feInsTitular = "20040703"
        val detalle = InConCod271Detalle()
        detalle.infBeneficio = "1"
        detalle.nuCobertura = "4"
        detalle.beMaxInicial = "50"
        detalle.moCobertura = "0.50"
        detalle.coInRestriccion = "1 "
        detalle.canServicio = "1"
        detalle.idProducto = "2"
        detalle.coTiCobertura = "4"
        detalle.coSubTiCobertura = "100"
        detalle.msgObs = "OBSERVACIONES"
        detalle.msgConEspeciales = "CONDICIONES ESPECIALES"
        detalle.coTiMoneda = "1"
        detalle.coPagoFijo = "60"
        detalle.coCalServicio = "Z3"
        detalle.canCalServicio = "1"
        detalle.coPagoVariable = "0.50"
        detalle.flagCaGarantia = "0"
        detalle.deflagCaGarantia = "Descripción Carta Garantía"
        detalle.feFinCarencia = "20100701"
        detalle.feFinEspera = "20040601"
        inConCod271.addDetalle(detalle)
        return inConCod271
    }

}