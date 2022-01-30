package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.config

import br.com.igorrodrigues.farmcontrol.infrastructure.persistence.user.TenantService
import org.flywaydb.core.Flyway
import org.springframework.stereotype.Component
import javax.sql.DataSource

@Component
class TenantServiceAdapter(private val dataSource: DataSource): TenantService {

    override fun initDatabase(schema: String) {
        Flyway.configure()
                .locations("db/migration/tenants")
                .dataSource(dataSource)
                .schemas(schema)
                .load()
                .migrate()
    }
}