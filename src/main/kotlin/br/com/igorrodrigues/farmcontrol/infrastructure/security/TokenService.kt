package br.com.igorrodrigues.farmcontrol.infrastructure.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class TokenService(
        @Value("\${jwt.expiration}") private val expiration: Long,
        @Value("\${jwt.secret}") private val secret: String,
) {

    fun generateToken(authenticate: Authentication): TokenDto {
        val user = authenticate.principal as Credentials
        return Jwts.builder()
                .setIssuer("API - FarmControl")
                .setIssuedAt(Date())
                .setSubject(user.username)
                .setExpiration(Date(Date().time + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact().let {
                    TokenDto(value = it)
                }
    }

    fun isValidToken(token: String): Boolean {
        return try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token)
            true
        } catch (e: Exception) {
            false
        }

    }

    fun getUserName(token: String) = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body.subject

}