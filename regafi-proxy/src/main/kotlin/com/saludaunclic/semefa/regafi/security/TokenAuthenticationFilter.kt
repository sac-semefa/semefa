package com.saludaunclic.semefa.regafi.security

import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class TokenAuthenticationFilter(requiresAuth: RequestMatcher):
    AbstractAuthenticationProcessingFilter(requiresAuth) {
    companion object {
        const val BEARER_HEADER: String = "Bearer"
        const val T_PARAM: String = "t"
    }

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        val param: String? = request?.getHeader(HttpHeaders.AUTHORIZATION) ?: request?.getParameter(T_PARAM)
        val token = param?.removePrefix(BEARER_HEADER)?.trim()
            ?: throw BadCredentialsException("Missing authentication token")
        val auth: Authentication = UsernamePasswordAuthenticationToken(token, token)
        return authenticationManager.authenticate(auth)
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?
    ) {
        super.successfulAuthentication(request, response, chain, authResult)
        chain?.doFilter(request, response)
    }
}
