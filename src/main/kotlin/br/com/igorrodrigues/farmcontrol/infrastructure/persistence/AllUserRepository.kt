package br.com.igorrodrigues.farmcontrol.infrastructure.persistence

import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface AllUserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}