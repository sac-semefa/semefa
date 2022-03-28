package com.saludaunclic.semefa.regafi.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pe.gob.susalud.jr.transaccion.susalud.service.imp.RegafiUpdate271ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service.imp.RegafiUpdate997ServiceImpl
import pe.gob.susalud.jr.transaccion.susalud.service2.RegafiUpdate271Service
import pe.gob.susalud.jr.transaccion.susalud.service2.RegafiUpdate997Service

@Configuration
class RegafiConfig {
    @Bean fun regafiUpdate271Service(): RegafiUpdate271Service = RegafiUpdate271ServiceImpl()

    @Bean fun regafiUpdate997Service(): RegafiUpdate997Service = RegafiUpdate997ServiceImpl()
}