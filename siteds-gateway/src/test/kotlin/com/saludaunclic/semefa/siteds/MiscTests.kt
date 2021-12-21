package com.saludaunclic.semefa.siteds

import com.fasterxml.jackson.databind.ObjectMapper
import com.saludaunclic.semefa.siteds.SitedsConstants.Transactions
import org.apache.commons.lang3.StringUtils
import org.junit.jupiter.api.Test
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ConDtad
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ConObs
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ResDeriva
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ResDerivaDetalle
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ResSctr
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ResSctrDetalle
import pe.gob.susalud.jr.transaccion.susalud.bean.In278ResCG
import pe.gob.susalud.jr.transaccion.susalud.bean.In278ResCGDetalle
import pe.gob.susalud.jr.transaccion.susalud.bean.In278SolCG
import pe.gob.susalud.jr.transaccion.susalud.bean.In997ResAut
import pe.gob.susalud.jr.transaccion.susalud.bean.InConAse270
import pe.gob.susalud.jr.transaccion.susalud.bean.InConCod271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConCod271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InConEntVinc278
import pe.gob.susalud.jr.transaccion.susalud.bean.InConMed271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConMed271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InConNom271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConNom271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InConProc271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConProc271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InRegAfi271
import pe.gob.susalud.jr.transaccion.susalud.bean.InRegAfi271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InResEntVinc278
import pe.gob.susalud.jr.transaccion.susalud.bean.InSolAut271
import pe.gob.susalud.jr.transaccion.susalud.bean.InSolAutExeCar271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InSolAutProEsp271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InSolAutRes271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InSolAutTieEsp271Detalle
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaType

class MiscTests {
    companion object {
        val lineSep: String = System.lineSeparator()
        const val STRING_LENGTH = 10;
    }

    private val objectMapper = ObjectMapper()
    private val printJson = true
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    private fun generateWord(): String = (1..STRING_LENGTH)
        .map { kotlin.random.Random.nextInt(0, charPool.size) }
        .map(charPool::get)
        .joinToString("");

    private fun <T> createEntity(constructor: KFunction<T>, vararg args: Any): T = constructor.call(*args)

    private fun <T : Any> populateFields(klass: KClass<T>): T {
        val obj: T = createEntity(klass.constructors.first())
        klass
            .memberProperties
            .forEach {
                val setter = klass
                    .memberFunctions
                    .find { f -> f.name == ("set" + it.name.replaceFirstChar { s-> s.uppercase() }) }
                if (it.returnType.javaType == String::class.java) {
                    setter?.call(obj, StringUtils.EMPTY)
                }
            }
        return obj
    }

    data class Leg<T>(val isRequest: Boolean, val txName: String, val data: T)

    private fun <T : Any> logLeg(operation: String, leg: Leg<T>) {
        with(leg) {
            val type: String = if (isRequest) "Request" else "Response"
            printJson("$operation - $type: $txName / ${data::class.java.simpleName}") {
                objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data)
            }
        }
    }

    private fun <T : Any> logLegs(operation: String, vararg legs: Leg<T>) {
        legs.forEach { logLeg(operation, it) }
    }

    private fun printSeparator(name: String, sep: String = "#") {
        val repeated = StringUtils.repeat(sep, 24)
        println("$lineSep $repeated $name $repeated $lineSep")
    }

    private fun printJson(name: String, data: () -> String) {
        if (printJson) {
            printSeparator(name)
            println(data.invoke())
        }
    }

    @Test
    fun getConsultaEntVinculada_test() {
        val req = populateFields(InConEntVinc278::class)
        val res = populateFields(InResEntVinc278::class)
        logLegs(
            "getConsultaEntVinculada",
            Leg(true, Transactions.REQ_278_CON_ENT_VINC, req),
            Leg(false, Transactions.RES_278_RES_ENT_VINC, res)
        )
    }

    @Test
    fun getConsultaAsegNom_test() {
        val req = populateFields(InConAse270::class)
        val res = populateFields(InConNom271::class)
        res.addDetalle(populateFields(InConNom271Detalle::class))
        logLegs(
            "getConsultaAsegNom",
            Leg(true, Transactions.REQ_270_CON_ASE, req),
            Leg(false, Transactions.RES_271_CON_NOM, res)
        )
    }

    @Test
    fun getConsultaAsegCod_test() {
        val req = "Mismo objeto ${Transactions.REQ_270_CON_ASE}"
        val res = populateFields(InConCod271::class)
        res.addDetalle(populateFields(InConCod271Detalle::class))
        logLegs(
            "getConsultaAsegCod",
            Leg(true, Transactions.REQ_270_CON_ASE, req),
            Leg(false, Transactions.RES_271_CON_COD, res)
        )
    }

    @Test
    fun getConsultaRegAfiliados_test() {
        val req = "Mismo objeto ${Transactions.REQ_270_CON_ASE}"
        val res = populateFields(InRegAfi271::class)
        res.addDetalle(populateFields(InRegAfi271Detalle::class))
        logLegs(
            "getConsultaRegAfiliados",
            Leg(true, Transactions.REQ_270_REGAFI, req),
            Leg(false, Transactions.RES_271_REGAFI, res)
        )
    }

    @Test
    fun getConsultaSCTR_test() {
        val req = "Mismo objeto ${Transactions.REQ_270_CON_ASE}"
        val res = populateFields(In271ResSctr::class)
        res.addDetalle(populateFields(In271ResSctrDetalle::class))
        logLegs(
            "getConsultaSCTR",
            Leg(true, Transactions.REQ_270_CON_ASE, req),
            Leg(false, Transactions.RES_271_RES_SCTR, res)
        )
    }

    @Test
    fun getRegistroDecAccidente_test() {
        val req = "Mismo objeto ${Transactions.REQ_270_CON_ASE}"
        val res = "Mismo objeto ${Transactions.RES_271_RES_SCTR}"
        logLegs(
            "getRegistroDecAccidente",
            Leg(true, Transactions.REQ_270_CON_ASE, req),
            Leg(false, Transactions.RES_271_RES_SCTR, res)
        )
    }

    @Test
    fun getConsultaDeriva_test() {
        val req = "Mismo objeto ${Transactions.REQ_270_CON_ASE}"
        val res = populateFields(In271ResDeriva::class)
        res.addDetalle(populateFields(In271ResDerivaDetalle::class))
        logLegs(
            "getConsultaDeriva",
            Leg(true, Transactions.REQ_270_CON_ASE, req),
            Leg(true, Transactions.RES_271_RES_DERIVA, res)
        )
    }

    @Test
    fun getConsultaProc_test() {
        val req = "Mismo objeto ${Transactions.REQ_270_CON_ASE}"
        val res = populateFields(InConProc271::class)
        res.addDetalle(populateFields(InConProc271Detalle::class))
        logLegs(
            "getConsultaProc",
            Leg(true, Transactions.REQ_270_CON_ASE, req),
            Leg(false, Transactions.RES_271_CON_PROC, res)
        )
    }

    @Test
    fun getConsultaDatosAdi_test() {
        val req = "Mismo objeto ${Transactions.REQ_270_CON_ASE}"
        val res = populateFields(In271ConDtad::class)
        logLegs(
            "getConsultaDatosAdi",
            Leg(true, Transactions.REQ_270_CON_ASE, req),
            Leg(false, Transactions.RES_271_CON_DTAD, res)
        )
    }

    @Test
    fun getCondicionMedica_test() {
        val req = "Mismo objeto ${Transactions.REQ_270_CON_ASE}"
        val res = populateFields(InConMed271::class)
        res.addDetalle(populateFields(InConMed271Detalle::class))
        logLegs(
            "getCondicionMedica",
            Leg(true, Transactions.REQ_270_CON_ASE, req),
            Leg(false, Transactions.RES_271_CON_MED, res)
        )
    }

    @Test
    fun getConsultaObservacion_test() {
        val req = "Mismo objeto ${Transactions.REQ_270_CON_ASE}"
        val res = populateFields(In271ConObs::class)
        logLegs(
            "getConsultaObservacion",
            Leg(true, Transactions.REQ_270_CON_ASE, req),
            Leg(false, Transactions.RES_271_CON_OBS, res)
        )
    }

    @Test
    fun getNumAutorizacion_test() {
        val req = populateFields(InSolAut271::class)
        req.addDetalle(populateFields(InSolAutProEsp271Detalle::class))
        req.addDetalle(populateFields(InSolAutTieEsp271Detalle::class))
        req.addDetalle(populateFields(InSolAutExeCar271Detalle::class))
        req.addDetalle(populateFields(InSolAutRes271Detalle::class))
        val res = populateFields(In997ResAut::class)
        logLegs(
            "getNumAutorizacion",
            Leg(true, Transactions.REQ_271_SOL_AUT, req),
            Leg(false, Transactions.RES_997_RES_AUT, res)
        )
    }

    @Test
    fun getConsultaxCartaGarantia_test() {
        val req = populateFields(In278SolCG::class)
        val res = populateFields(In278ResCG::class)
        res.addDetalle(populateFields(In278ResCGDetalle::class))
        logLegs(
            "getConsultaxCartaGarantia",
            Leg(true, Transactions.REQ_278_SOL_CG, req),
            Leg(false, Transactions.RES_278_RES_CG, res)
        )
    }
}
