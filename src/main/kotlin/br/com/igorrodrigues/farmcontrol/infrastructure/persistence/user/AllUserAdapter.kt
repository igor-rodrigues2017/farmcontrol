package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.user

import br.com.igorrodrigues.farmcontrol.domain.model.user.AllUser
import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import br.com.igorrodrigues.farmcontrol.domain.model.user.UserNotFoundException
import org.springframework.stereotype.Repository

@Repository
class AllUserAdapter(private val repository: AllUserRepository, val tenantService: TenantService) : AllUser {
    override fun save(user: User): User {
        val userDataSaved = repository.save(UserData.from(user))
        //TODO: MELHOR VER SE AQUI É UM BOM LUGAR E FAZER TESTES
        tenantService.initDatabase(userDataSaved.email)
        return userDataSaved.toUser()
    }

    override fun withEmail(email: String): User =
        repository.findByEmail(email)?.toUser() ?: throw UserNotFoundException()

    override fun userAlreadyExist(email: String): Boolean {
        if (repository.findByEmail(email) == null) return false
        return true
    }
}