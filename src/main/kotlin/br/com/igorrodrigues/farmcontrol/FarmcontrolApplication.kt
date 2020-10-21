package br.com.igorrodrigues.farmcontrol

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.web.servlet.invoke

@SpringBootApplication
class FarmcontrolApplication

@EnableWebSecurity
class FarmControlSecurityConfiguration : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http {
            httpBasic {}
            authorizeRequests {
                authorize("/**", permitAll)
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<FarmcontrolApplication>(*args)
}
