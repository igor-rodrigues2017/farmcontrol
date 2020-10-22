package br.com.igorrodrigues.farmcontrol.infrastructure.persistence

import br.com.igorrodrigues.farmcontrol.domain.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface AllUserRepository: JpaRepository<User, Long> {}