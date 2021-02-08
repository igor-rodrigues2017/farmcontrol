package br.com.igorrodrigues.farmcontrol.infrastructure.persistence

import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class TenantIdentifierResolver: CurrentTenantIdentifierResolver {

    override fun resolveCurrentTenantIdentifier(): String {
        return when(val authentication = SecurityContextHolder.getContext().authentication) {
            is AnonymousAuthenticationToken, null -> DEFAULT_TENANT
            else -> authentication.principal.toString()
        }
    }

    override fun validateExistingCurrentSessions() = true

    companion object {
        const val DEFAULT_TENANT = "default"
    }

}