package br.com.igorrodrigues.farmcontrol.application.usecase.user

import br.com.igorrodrigues.farmcontrol.domain.model.user.AllUser
import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import org.springframework.stereotype.Service

@Service
class CreateUserUseCase(private val allUser: AllUser) {

    fun create(userDto: UserDto): User {
        if (allUser.userAlreadyExist(userDto.email)) throw UserAlreadyExistentException()
        return allUser.save(User(email = userDto.email, password = userDto.password))
    }

    class UserAlreadyExistentException : RuntimeException("User already existent")
}