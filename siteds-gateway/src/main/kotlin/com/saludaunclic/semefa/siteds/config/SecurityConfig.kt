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
class SecurityConfig: WebSecurityConfigurerAdapter() {
    companion object {
        private const val SOAP_API_PATTERN: String = "/api/soap/**"
        private const val ACTUATOR_PATTERN: String = "/actuator/**"
        private val PUBLIC_URLS: RequestMatcher = OrRequestMatcher(
            AntPathRequestMatcher(SOAP_API_PATTERN),
            AntPathRequestMatcher(ACTUATOR_PATTERN))
        private val PROTECTED_URLS: RequestMatcher = NegatedRequestMatcher(PUBLIC_URLS)
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().requestMatchers(PUBLIC_URLS)
    }

    override fun configure(http: HttpSecurity) {
        http
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS)
            .and()
            .authorizeRequests().requestMatchers(PROTECTED_URLS)
            .authenticated()
            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable()
    }

    @Bean fun forbiddenEntryPoint(): AuthenticationEntryPoint = HttpStatusEntryPoint(HttpStatus.FORBIDDEN)
}

