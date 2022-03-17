package com.saludaunclic.semefa.siteds.model

import pe.gob.susalud.jr.transaccion.susalud.bean.In271ConDtad
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ConObs
import pe.gob.susalud.jr.transaccion.susalud.bean.In271ResSctr
import pe.gob.susalud.jr.transaccion.susalud.bean.In278ResCG
import pe.gob.susalud.jr.transaccion.susalud.bean.In997ResAut
import pe.gob.susalud.jr.transaccion.susalud.bean.InConCod271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConMed271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConNom271
import pe.gob.susalud.jr.transaccion.susalud.bean.InConProc271
import pe.gob.susalud.jr.transaccion.susalud.bean.InRegAfi271
import pe.gob.susalud.jr.transaccion.susalud.bean.InResEntVinc278

open class Response<T> {
    var error: Int = 0
    var message: String? = null
    var data: T? = null
}

class ResponseInResEntVinc278: Response<InResEntVinc278>()
class ResponseInConCod271: Response<InConCod271>()
class ResponseIn271ResSctr: Response<In271ResSctr>()
class ResponseInRegAfi271: Response<InRegAfi271>()
class ResponseInConMed271: Response<InConMed271>()
class ResponseIn997ResAut: Response<In997ResAut>()
class ResponseIn278ResCG: Response<In278ResCG>()
class ResponseInConNom271: Response<InConNom271>()
class ResponseInConProc271: Response<InConProc271>()
class ResponseIn271ConObs: Response<In271ConObs>()
class ResponseIn271ConDtad: Response<In271ConDtad>()