package com.saludaunclic.semefa.siteds.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConCod271Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConEntVinc278Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConMed271Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConNom271Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ConDtadService
import pe.gob.susalud.jr.transaccion.susalud.service.In271ConObsService
import pe.gob.susalud.jr.transaccion.susalud.service.In271ConProcService
import pe.gob.susalud.jr.transaccion.susalud.service.In271ResSctrService
import pe.gob.susalud.jr.transaccion.susalud.service.In278ResCGService
import pe.gob.susalud.jr.transaccion.susalud.service.In278SolCGService
import pe.gob.susalud.jr.transaccion.susalud.service.In997ResAutService
import pe.gob.susalud.jr.transaccion.susalud.service.RegAfi270Service
import pe.gob.susalud.jr.transaccion.susalud.service.RegAfi271Service
import pe.gob.susalud.jr.transaccion.susalud.service.ResEntVinc278Service
import pe.gob.susalud.jr.transaccion.susalud.service.SolAut271Service
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConAse270ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConCod271ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConEntVinc278ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConMed271ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConNom271ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.In271ConDtadServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.In271ConObsServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.In271ConProcServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.In271ResSctrServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.In278ResCGServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.In278SolCGServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.In997ResAutServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.RegAfi270ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.RegAfi271ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ResEntVinc278ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.SolAut271ServiceImpl

@Configuration
class SitedsConfig {
    //getConsultaEntVinculada
    @Bean fun conEntVinc278Service(): ConEntVinc278Service = ConEntVinc278ServiceImpl()

    @Bean fun resEntVinc278Service(): ResEntVinc278Service = ResEntVinc278ServiceImpl()

    // getConsultaAsegNom - getConsultaAsegCod
    @Bean fun conAse270Service(): ConAse270Service = ConAse270ServiceImpl()

    @Bean fun conNom271Service(): ConNom271Service = ConNom271ServiceImpl()

    @Bean fun conCod271Service(): ConCod271Service = ConCod271ServiceImpl()

    // getConsultaRegAfiliados
    @Bean fun regAfi270Service(): RegAfi270Service = RegAfi270ServiceImpl()

    @Bean fun regAfi271Service(): RegAfi271Service = RegAfi271ServiceImpl()

    // getConsultaSCTR - getRegistroDecAccidente
    @Bean fun in271ResSctrService(): In271ResSctrService = In271ResSctrServiceImpl()

    // getConsultaProc
    @Bean fun in271ConProcService(): In271ConProcService = In271ConProcServiceImpl()

    //getConsultaDatosAdi
    @Bean fun in271ConDtadService(): In271ConDtadService = In271ConDtadServiceImpl()

    //getCondicionMedica
    @Bean fun conMed271Service(): ConMed271Service = ConMed271ServiceImpl()

    // getConsultaObservacion
    @Bean fun in271ConObsService(): In271ConObsService = In271ConObsServiceImpl()

    // getNumAutorizacion
    @Bean fun solAut271Service(): SolAut271Service = SolAut271ServiceImpl()

    @Bean fun in997ResAutService(): In997ResAutService = In997ResAutServiceImpl()

    // getConsultaxCartaGarantia
    @Bean fun in278SolCGService(): In278SolCGService = In278SolCGServiceImpl()

    @Bean fun in278ResCGService(): In278ResCGService = In278ResCGServiceImpl()
}