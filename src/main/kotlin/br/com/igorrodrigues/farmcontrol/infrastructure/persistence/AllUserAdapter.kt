package br.com.igorrodrigues.farmcontrol.infrastructure.persistence

import br.com.igorrodrigues.farmcontrol.domain.model.user.AllUser
import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import br.com.igorrodrigues.farmcontrol.domain.model.user.UserNotFoundException
import org.springframework.stereotype.Repository

@Repository
class AllUserAdapter(private val repository: AllUserRepository): AllUser {
    override fun save(user: User) = repository.save(user)
    override fun withEmail(email: String): User = repository.findByEmail(email) ?: throw UserNotFoundException()
    override fun userAlreadyExist(email: String): Boolean {
        if (repository.findByEmail(email) == null) return false
        return true
    }
}