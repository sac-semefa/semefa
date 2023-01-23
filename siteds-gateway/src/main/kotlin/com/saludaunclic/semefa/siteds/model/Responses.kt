package com.saludaunclic.semefa.siteds.model

import com.fasterxml.jackson.annotation.JsonIgnore
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ConDtad
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ConObs
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ResSctr
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ResSctrDetalle
import pe.gob.susalud.jr.transaccion.susalud.bean.In278ResCG
import pe.gob.susalud.jr.transaccion.susalud.bean.In278ResCGDetalle
import pe.gob.susalud.jr.transaccion.susalud.bean.In997ResAut
import pe.gob.susalud.jr.transaccion.susalud.bean.InConCod271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConCod271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InConMed271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConMed271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InConNom271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConNom271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InConProc271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConProc271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InRegAfi271
import pe.gob.susalud.jr.transaccion.susalud.bean.InRegAfi271Detalle
import pe.gob.susalud.jr.transaccion.susalud.bean.InResEntVinc278

open class Response<T> {
    var error: Int = 0
    var message: String? = null
    var data: T? = null
}

class ResponseInResEntVinc278: Response<InResEntVinc278>()

class InConCod271_Deduped: InConCod271() {
    @JsonIgnore
    override fun getDetalles(): MutableList<InConCod271Detalle> {
        return super.getDetalles()
    }
}
class ResponseInConCod271: Response<InConCod271_Deduped>()

class In271ResSctr_Deduped: In271ResSctr() {
    @JsonIgnore
    override fun getDetalles(): MutableList<In271ResSctrDetalle> {
        return super.getDetalles()
    }
}
class ResponseIn271ResSctr: Response<In271ResSctr_Deduped>()

class InRegAfi271_Deduped: InRegAfi271() {
    @JsonIgnore
    override fun getDetalles(): MutableList<InRegAfi271Detalle> {
        return super.getDetalles()
    }
}
class ResponseInRegAfi271: Response<InRegAfi271_Deduped>()

class InConMed271_Deduped: InConMed271() {
    @JsonIgnore
    override fun getDetalles(): MutableList<InConMed271Detalle> {
        return super.getDetalles()
    }
}
class ResponseInConMed271: Response<InConMed271_Deduped>()

class ResponseIn997ResAut: Response<In997ResAut>()

class In278ResCG_Deduped: In278ResCG() {
    @JsonIgnore
    override fun getDetalles(): MutableList<In278ResCGDetalle> {
        return super.getDetalles()
    }
}
class ResponseIn278ResCG: Response<In278ResCG_Deduped>()

class InConNom271_Deduped: InConNom271() {
    @JsonIgnore
    override fun getDetalles(): MutableList<InConNom271Detalle> {
        return super.getDetalles()
    }
}
class ResponseInConNom271: Response<InConNom271_Deduped>()

class InConProc271_Deduped: InConProc271() {
    @JsonIgnore
    override fun getDetalles(): MutableList<InConProc271Detalle> {
        return super.getDetalles()
    }
}
class ResponseInConProc271: Response<InConProc271_Deduped>()

class ResponseIn271ConObs: Response<In271ConObs>()

class ResponseIn271ConDtad: Response<In271ConDtad>()
