package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.config

import br.com.igorrodrigues.farmcontrol.infrastructure.security.Credentials
import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class TenantIdentifierResolver: CurrentTenantIdentifierResolver {

    override fun resolveCurrentTenantIdentifier(): String {
        val authentication = retrieveAuthentication()
        return when {
            authentication is AnonymousAuthenticationToken || authentication == null -> DEFAULT_TENANT
            else -> getTenant(authentication)
        }
    }

    open fun retrieveAuthentication(): Authentication? = SecurityContextHolder.getContext().authentication

    private fun getTenant(authentication: Authentication): String {
        val principal = authentication.principal

        if (principal is Credentials) {
            return principal.username
        }
        return DEFAULT_TENANT
    }

    override fun validateExistingCurrentSessions() = true

    companion object {
        const val DEFAULT_TENANT = "default"
    }

}