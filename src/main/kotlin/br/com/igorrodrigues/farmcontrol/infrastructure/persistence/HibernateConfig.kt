package br.com.igorrodrigues.farmcontrol.infrastructure.persistence

import br.com.igorrodrigues.farmcontrol.FarmcontrolApplication
import org.hibernate.MultiTenancyStrategy
import org.hibernate.cfg.Environment
import org.hibernate.context.spi.CurrentTenantIdentifierResolver
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.jpa.JpaVendorAdapter
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter
import java.util.*
import javax.sql.DataSource


@Configuration
class HibernateConfig {

    @Bean
    fun entityManagerFactory(
            dataSource: DataSource,
            jpaProperties: JpaProperties,
            multiTenantConnectionProvider: MultiTenantConnectionProvider,
            tenantIdentifierResolver: CurrentTenantIdentifierResolver,
    ): LocalContainerEntityManagerFactoryBean {
        val entityManager = LocalContainerEntityManagerFactoryBean()
        entityManager.dataSource = dataSource
        entityManager.setPackagesToScan(FarmcontrolApplication::class.java.getPackage().name)
        entityManager.jpaVendorAdapter = jpaVendorAdapter()
        val jpaPropertiesMap: MutableMap<String, Any?> = HashMap(jpaProperties.properties)
        jpaPropertiesMap[Environment.MULTI_TENANT] = MultiTenancyStrategy.SCHEMA
        jpaPropertiesMap[Environment.MULTI_TENANT_CONNECTION_PROVIDER] = multiTenantConnectionProvider
        jpaPropertiesMap[Environment.MULTI_TENANT_IDENTIFIER_RESOLVER] = tenantIdentifierResolver
        entityManager.setJpaPropertyMap(jpaPropertiesMap)
        return entityManager
    }

    @Bean
    fun jpaVendorAdapter(): JpaVendorAdapter = HibernateJpaVendorAdapter()
}
