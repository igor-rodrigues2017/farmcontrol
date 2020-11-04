package br.com.igorrodrigues.farmcontrol.infrastructure.security

data class TokenDto(
        val value: String = "",
        val type: String = "Bearer",
)