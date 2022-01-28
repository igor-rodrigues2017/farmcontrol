package br.com.igorrodrigues.farmcontrol.domain.model.user

data class User(
    val id: Long = 0,
    val email: String = "",
    val password: String = ""
)