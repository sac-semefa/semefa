package com.saludaunclic.semefa.siteds.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pe.gob.susalud.jr.transaccion.susalud.service.ConAse270Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConCod271Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConEntVinc278Service
import pe.gob.susalud.jr.transaccion.susalud.service.ConNom271Service
import pe.gob.susalud.jr.transaccion.susalud.service.In271ResSctrService
import pe.gob.susalud.jr.transaccion.susalud.service.RegAfi270Service
import pe.gob.susalud.jr.transaccion.susalud.service.RegAfi271Service
import pe.gob.susalud.jr.transaccion.susalud.service.ResEntVinc278Service
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConAse270ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConCod271ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConEntVinc278ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ConNom271ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.In271ResSctrServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.RegAfi270ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.RegAfi271ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.ResEntVinc278ServiceImpl

@Configuration
class SitedsConfig {
    @Bean fun resEntVinc278Service(): ResEntVinc278Service = ResEntVinc278ServiceImpl()

    @Bean fun conEntVinc278Service(): ConEntVinc278Service = ConEntVinc278ServiceImpl()

    @Bean fun conAse270Service(): ConAse270Service = ConAse270ServiceImpl()

    @Bean fun conNom271Service(): ConNom271Service = ConNom271ServiceImpl()

    @Bean fun conCod271Service(): ConCod271Service = ConCod271ServiceImpl()

    @Bean fun regAfi270Service(): RegAfi270Service = RegAfi270ServiceImpl()

    @Bean fun regAfi271Service(): RegAfi271Service = RegAfi271ServiceImpl()

    @Bean fun in271ResSctrService(): In271ResSctrService = In271ResSctrServiceImpl()
}