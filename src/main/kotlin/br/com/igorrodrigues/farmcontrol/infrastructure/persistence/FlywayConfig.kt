package br.com.igorrodrigues.farmcontrol.infrastructure.persistence

import org.flywaydb.core.Flyway
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class FlywayConfig {

    @Bean
    fun flyway(dataSource: DataSource): Flyway {
        val flyway = Flyway.configure()
                .locations("db/migration/default")
                .dataSource(dataSource)
                .schemas(TenantIdentifierResolver.DEFAULT_TENANT)
                .load()
        flyway.migrate()
        return flyway
    }

    @Bean
    fun commandLineRunner(
            allUser: AllUserRepository,
            dataSource: DataSource,
    ): CommandLineRunner {
        return CommandLineRunner {
            allUser.findAll().forEach {
                Flyway.configure()
                        .locations("db/migration/tenants")
                        .dataSource(dataSource)
                        .schemas(it.email)
                        .load()
                        .migrate()
            }
        }

    }
}