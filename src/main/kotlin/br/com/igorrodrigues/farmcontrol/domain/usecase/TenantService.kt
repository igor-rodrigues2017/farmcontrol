package br.com.igorrodrigues.farmcontrol.domain.usecase

interface TenantService {
    fun initDatabase(schema: String)
}