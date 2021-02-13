package br.com.igorrodrigues.farmcontrol.domain.usecase.user

interface TenantService {
    fun initDatabase(schema: String)
}