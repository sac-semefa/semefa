package com.saludaunclic.semefa.siteds

import org.junit.jupiter.api.Test
import pe.gob.susalud.jr.transaccion.susalud.bean.InConEntVinc278
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConEntVinc278ServiceImpl

class MiscTests {
    @Test
    fun conEntVinc278_test() {
        val conEntVinc278Service = ConEntVinc278ServiceImpl()
        val inConEntVinc278: InConEntVinc278 = generarInSolEntVinc278()
        val x12 = conEntVinc278Service.beanToX12N(inConEntVinc278)
    }

    private fun generarInSolEntVinc278(): InConEntVinc278 {
        val inSolEntVinc278 = InConEntVinc278()
        inSolEntVinc278.noTransaccion = "278_CON_ENT_VINC"
        inSolEntVinc278.idRemitente = "98001C         "
        inSolEntVinc278.idReceptor = "30009          "
        inSolEntVinc278.feTransaccion = "20150311"
        inSolEntVinc278.hoTransaccion = "152200"
        inSolEntVinc278.idCorrelativo = "756496608"
        inSolEntVinc278.idTransaccion = "278"
        inSolEntVinc278.tiFinalidad = "13"
        inSolEntVinc278.caIPRESS = "2"
        inSolEntVinc278.noIPRESS = "SEMEFA"
        inSolEntVinc278.tiDoIPRESS = "6"
        inSolEntVinc278.nuRucIPRESS = "20100121809"
        return inSolEntVinc278
    }
}