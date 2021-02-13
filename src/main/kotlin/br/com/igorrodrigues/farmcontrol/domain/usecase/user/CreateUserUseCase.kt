package br.com.igorrodrigues.farmcontrol.domain.usecase.user

import br.com.igorrodrigues.farmcontrol.domain.model.user.AllUser
import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import org.springframework.stereotype.Service

@Service
class CreateUserUseCase(private val allUser: AllUser,
                        private val tenantService: TenantService
) {

    fun create(userDto: UserDto): User {
        if (allUser.userAlreadyExist(userDto.email)) throw UserAlreadyExistentException()
        val saved = allUser.save(User(email = userDto.email, password = userDto.password))
        //TODO: Maybe tenantService could be handled in persistence layer instead here.
        tenantService.initDatabase(saved.email)
        return saved
    }

    class UserAlreadyExistentException : RuntimeException("User already existent")
}