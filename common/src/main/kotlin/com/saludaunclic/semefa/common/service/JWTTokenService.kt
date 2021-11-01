package com.saludaunclic.semefa.common.service

import com.saludaunclic.semefa.common.config.TokenProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Clock
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.HS256
import io.jsonwebtoken.impl.compression.GzipCompressionCodec
import org.springframework.stereotype.Service
import java.util.Base64
import java.util.Date

@Service
class JWTTokenService(
    private val dateService: DateService,
    private val tokenProperties: TokenProperties
): Clock, TokenService {
    private val encodedSecret: String = Base64.getEncoder().encodeToString(tokenProperties.secret.toByteArray())

    companion object {
        const val DOT = '.'
        val compressionCodec: GzipCompressionCodec = GzipCompressionCodec()
    }

    override fun now(): Date = dateService.toDate(dateService.now())

    override fun permanent(attributes: Map<String, String>): String = newToken(attributes, 0)

    override fun expiring(attributes: Map<String, String>): String = newToken(attributes, tokenProperties.expirationSec)

    override fun untrusted(token: String): Map<String, String> =
        parseClaims {
            Jwts.parser()
                .requireIssuer(tokenProperties.issuer)
                .setClock(this)
                .setAllowedClockSkewSeconds(tokenProperties.clockSkewSec)
                .parseClaimsJws(token.substringBeforeLast(DOT))
                .body
        }

    override fun verify(token: String): Map<String, String> =
        parseClaims {
            Jwts.parser()
                .requireIssuer(tokenProperties.issuer)
                .setClock(this)
                .setAllowedClockSkewSeconds(tokenProperties.clockSkewSec)
                .setSigningKey(encodedSecret)
                .parseClaimsJws(token)
                .body
        }

    private fun newToken(attributes: Map<String, String>, expiresInSec: Long): String {
        val now = dateService.now()
        val claims: Claims = Jwts
            .claims()
            .setIssuer(tokenProperties.issuer)
            .setIssuedAt(dateService.toDate(now))
        if (expiresInSec > 0) {
            claims.expiration = dateService.toDate(now.plusSeconds(expiresInSec))
        }
        claims.putAll(attributes)

        return Jwts
            .builder()
            .setClaims(claims)
            .signWith(HS256, encodedSecret)
            .compressWith(compressionCodec)
            .compact()
    }

    private fun parseClaims(toClaims: () -> Claims): Map<String, String> =
        toClaims().entries.associate { it.key to it.value.toString() }
}
