package com.saludaunclic.semefa.siteds.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(val cxfProperties: CxfProperties): WebSecurityConfigurerAdapter() {
    @Bean fun publicUrls(): RequestMatcher = OrRequestMatcher(
        AntPathRequestMatcher("${cxfProperties.path}/**"),
        AntPathRequestMatcher("/actuator/**"))

    @Bean fun protectedUrls(): RequestMatcher = NegatedRequestMatcher(publicUrls())

    override fun configure(web: WebSecurity) {
        web.ignoring().requestMatchers(publicUrls())
    }

    override fun configure(http: HttpSecurity) {
        val protectedUrls = protectedUrls()
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), protectedUrls)
            .and()
            .authorizeRequests().requestMatchers(protectedUrls)
            .authenticated()
            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable()
    }

    @Bean fun forbiddenEntryPoint(): AuthenticationEntryPoint = HttpStatusEntryPoint(HttpStatus.FORBIDDEN)
}

