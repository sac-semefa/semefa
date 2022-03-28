package com.saludaunclic.semefa.siteds.config

import com.saludaunclic.semefa.common.security.NoRedirectStrategy
import com.saludaunclic.semefa.common.security.TokenAuthenticationFilter
import com.saludaunclic.semefa.common.security.TokenAuthenticationProvider
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.Filter

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(val provider: TokenAuthenticationProvider): WebSecurityConfigurerAdapter() {
    companion object {
        private val PUBLIC_URLS: RequestMatcher = OrRequestMatcher(
            AntPathRequestMatcher("/siteds_ws/**"),
            AntPathRequestMatcher("/api/public/**"),
            AntPathRequestMatcher("/actuator/**"),
            AntPathRequestMatcher("/error/**"))
        private val PROTECTED_URLS: RequestMatcher = NegatedRequestMatcher(PUBLIC_URLS)
    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(provider)
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
            .authenticationProvider(provider)
            .addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter::class.java)
            .authorizeRequests().requestMatchers(PROTECTED_URLS)
            .authenticated()
            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .logout().disable()
    }
    @Bean
    fun restAuthenticationFilter(): TokenAuthenticationFilter =
        TokenAuthenticationFilter(PROTECTED_URLS).apply {
            setAuthenticationManager(authenticationManager())
            setAuthenticationSuccessHandler(successHandler())
        }

    @Bean
    fun successHandler(): SimpleUrlAuthenticationSuccessHandler =
        SimpleUrlAuthenticationSuccessHandler().apply { setRedirectStrategy(NoRedirectStrategy()) }

    @Bean
    fun disableAutoRegistration(filter: TokenAuthenticationFilter): FilterRegistrationBean<*> =
        FilterRegistrationBean<Filter>(filter).apply { isEnabled = false }

    @Bean fun forbiddenEntryPoint(): AuthenticationEntryPoint = HttpStatusEntryPoint(HttpStatus.FORBIDDEN)
}

