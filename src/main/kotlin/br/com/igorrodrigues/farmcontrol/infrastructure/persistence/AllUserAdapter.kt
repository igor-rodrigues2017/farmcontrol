package br.com.igorrodrigues.farmcontrol.infrastructure.persistence

import br.com.igorrodrigues.farmcontrol.domain.model.AllUser
import br.com.igorrodrigues.farmcontrol.domain.model.User
import br.com.igorrodrigues.farmcontrol.domain.model.UserNotFoundException
import org.springframework.stereotype.Repository

@Repository
class AllUserAdapter(private val repository: AllUserRepository): AllUser {
    override fun save(user: User) = repository.save(user)
    override fun withEmail(email: String): User = repository.findByEmail(email) ?: throw UserNotFoundException()
}