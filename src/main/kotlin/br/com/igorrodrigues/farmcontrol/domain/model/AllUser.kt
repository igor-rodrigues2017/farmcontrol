package br.com.igorrodrigues.farmcontrol.domain.model

import org.springframework.data.jpa.repository.JpaRepository

interface AllUser: JpaRepository<User, Long> {}
