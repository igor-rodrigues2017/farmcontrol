package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.config

import br.com.igorrodrigues.farmcontrol.infrastructure.persistence.config.TenantIdentifierResolver
import br.com.igorrodrigues.farmcontrol.infrastructure.persistence.config.TenantIdentifierResolver.Companion.DEFAULT_TENANT
import br.com.igorrodrigues.farmcontrol.infrastructure.security.Credentials
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks
import org.mockito.Spy
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication

internal class TenantIdentifierResolverTest {

    private val TENANT = "tenant"

    @Spy
    private lateinit var tenantIdentifierResolver: TenantIdentifierResolver

    @Mock
    private lateinit var authentication: Authentication

    @Mock
    private lateinit var credentials: Credentials

    @BeforeEach
    internal fun setUp() {
        openMocks(this)
    }

    @Test
    internal fun `should identified tenente from authentications`() {
        whenever(credentials.username).thenReturn(TENANT)
        whenever(authentication.principal).thenReturn(credentials)
        doReturn(authentication).whenever(tenantIdentifierResolver).retrieveAuthentication()
        val tenantIdentifier = tenantIdentifierResolver.resolveCurrentTenantIdentifier()
        assertEquals(TENANT, tenantIdentifier)
    }

    @Test
    internal fun `should return default tenente when authentication is null`() {
        doReturn(null).whenever(tenantIdentifierResolver).retrieveAuthentication()
        val tenantIdentifier = tenantIdentifierResolver.resolveCurrentTenantIdentifier()
        assertEquals(DEFAULT_TENANT, tenantIdentifier)
    }

    @Test
    internal fun `should return default tenente when authentication is AnonymousAuthenticationToken`() {
        doReturn(anyAnonymousToken()).whenever(tenantIdentifierResolver).retrieveAuthentication()
        val tenantIdentifier = tenantIdentifierResolver.resolveCurrentTenantIdentifier()
        assertEquals(DEFAULT_TENANT, tenantIdentifier)
    }

    private fun anyAnonymousToken(): AnonymousAuthenticationToken = mock()
}