package br.com.igorrodrigues.farmcontrol.domain.model

interface AllUser {
    fun save(user: User): User
    fun withEmail(email: String): User
    fun userAlreadyExist(email: String): Boolean
}

class UserNotFoundException : RuntimeException("User not found")