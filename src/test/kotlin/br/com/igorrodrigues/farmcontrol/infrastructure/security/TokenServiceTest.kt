package br.com.igorrodrigues.farmcontrol.infrastructure.security

import br.com.igorrodrigues.farmcontrol.domain.model.User
import io.jsonwebtoken.Jwts
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.security.core.Authentication

internal class TokenServiceTest {

    companion object {
        const val SECRET = "secret"
        const val EMAIL = "test@test.com"
    }

    @Mock
    private lateinit var authenticate: Authentication

    @BeforeEach
    internal fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(authenticate.principal).thenReturn(Credentials(User(email = EMAIL, password = "1234")))
    }

    @Test
    internal fun `should generate a valid token`() {
        val token = TokenService(expiration = 18000, secret = SECRET).generateToken(authenticate)
        val claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).body
        assertThat(claims.subject, equalTo(EMAIL))
        assertThat(claims.issuer, equalTo("API - FarmControl"))
    }

    @Test
    internal fun `should validate a valid token`() {
        val tokenService = TokenService(expiration = 18000, secret = SECRET)
        val token = tokenService.generateToken(authenticate)
        assertTrue(tokenService.isValidToken(token))
    }

    @Test
    internal fun `should validate a wrong token`() {
        assertFalse(TokenService(expiration = 18000, secret = SECRET).isValidToken("eyJhbGciOiJIUzI1NgJ9.eyJpc3viOiJBUEkgLSBGYXJtQ29udHJvbCIsImlhdCI6MTYwNDM2MDQ2MSwic3ViIjoidGVzdEB0xXNrLJNvbSIsImV4cCI6MTYwNDM2MDQ3OX0.Y5R4G8fTGmRcZkPDcgWbgpd6rcdPGCtNhmrxeY2HrGE"))
    }

}