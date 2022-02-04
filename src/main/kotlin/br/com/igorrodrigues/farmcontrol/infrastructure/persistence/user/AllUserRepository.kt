package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.user

import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface AllUserRepository: JpaRepository<UserData, Long> {
    fun findByEmail(email: String): UserData?
}