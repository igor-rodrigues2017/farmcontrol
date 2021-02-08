package br.com.igorrodrigues.farmcontrol.infrastructure.persistence

import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider
import org.springframework.stereotype.Component
import java.sql.Connection
import javax.sql.DataSource

@Component
class TenantConnectionProvider(private val dataSource: DataSource): MultiTenantConnectionProvider {

    override fun isUnwrappableAs(unwrapType: Class<*>?): Boolean = false

    override fun <T : Any?> unwrap(unwrapType: Class<T>?): T? {
       return null
    }

    override fun getAnyConnection(): Connection = dataSource.connection

    override fun releaseAnyConnection(connection: Connection?) {
        connection?.close()
    }

    override fun getConnection(tenantIdentifier: String?): Connection {
        val connection = anyConnection
        connection.createStatement().execute("SET SCHEMA '$tenantIdentifier'")
        return connection
    }

    override fun releaseConnection(tenantIdentifier: String?, connection: Connection?) {
        connection?.createStatement()?.execute("SET SCHEMA '$(TenantIdentifierResolver.DEFAULT_TENANT)'")
        releaseAnyConnection(connection)
    }

    override fun supportsAggressiveRelease(): Boolean = false
}