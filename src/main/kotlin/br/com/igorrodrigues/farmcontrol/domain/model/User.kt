package br.com.igorrodrigues.farmcontrol.domain.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
@Table(name = "users")
data class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val email: String = "",
        @JsonIgnore
        val password: String = ""
)