package br.com.igorrodrigues.farmcontrol.domain.useCase

import br.com.igorrodrigues.farmcontrol.domain.model.AllUser
import br.com.igorrodrigues.farmcontrol.domain.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CreateUserUseCase(@Autowired private val allUser: AllUser) {

    fun create(userDto: UserDto): User {
        if (allUser.userAlreadyExist(userDto.email)) throw UserAlreadyExistentException()
        return allUser.save(User(email = userDto.email, password = userDto.password))
    }

    class UserAlreadyExistentException : RuntimeException("User already existent")
}