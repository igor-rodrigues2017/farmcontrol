package br.com.igorrodrigues.farmcontrol.infrastructure.security

import br.com.igorrodrigues.farmcontrol.domain.model.AllUser
import br.com.igorrodrigues.farmcontrol.domain.model.UserNotFoundException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AuthenticationService(private val allUser: AllUser): UserDetailsService {

    override fun loadUserByUsername(username: String?) = username?.let { Credentials(allUser.withEmail(it)) } ?: throw UserNotFoundException()
}