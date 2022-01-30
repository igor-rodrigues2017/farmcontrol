package br.com.igorrodrigues.farmcontrol.application.usecase.user

interface TenantService {
    fun initDatabase(schema: String)
}