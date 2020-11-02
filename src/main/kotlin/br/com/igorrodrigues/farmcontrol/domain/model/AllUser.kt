package br.com.igorrodrigues.farmcontrol.domain.model

interface AllUser {
    fun save(user: User): User
    fun withEmail(email: String): User
}

class UserNotFoundException : RuntimeException("User not found")