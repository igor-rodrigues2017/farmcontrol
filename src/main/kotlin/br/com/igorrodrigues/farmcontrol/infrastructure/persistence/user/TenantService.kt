package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.user

interface TenantService {
    fun initDatabase(schema: String)
}