package br.com.igorrodrigues.farmcontrol.infrastructure.security

import br.com.igorrodrigues.farmcontrol.domain.model.AllUser
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationTokenFilter(
        private val tokenService: TokenService,
        private val allUser: AllUser,
): OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val token = retrieveToken(request)
        authenticateUser(token)
        filterChain.doFilter(request, response)
    }

    private fun authenticateUser(token: String) {
        if (tokenService.isValidToken(token)) {
            val user = allUser.withEmail(tokenService.getUserName(token))
            val credential = Credentials(user)
            SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(credential, null, credential.authorities)
        }
    }

    private fun retrieveToken(request: HttpServletRequest): String {
        return request.getHeader("Authorization").let {
            when {
                it.isNullOrEmpty() || !it.startsWith("Bearer") -> "INVALID"
                else -> it.substring(6)
            }
        }
    }
}