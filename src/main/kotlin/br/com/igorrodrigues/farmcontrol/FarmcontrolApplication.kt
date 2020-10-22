package br.com.igorrodrigues.farmcontrol

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@SpringBootApplication
@EnableSpringDataWebSupport
class FarmcontrolApplication

@EnableWebSecurity
class FarmControlSecurityConfiguration : WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity?) {
        http {
            httpBasic {}
            csrf { disable() }
            sessionManagement { sessionCreationPolicy = SessionCreationPolicy.STATELESS }
            authorizeRequests {
                authorize(AntPathRequestMatcher("/signin", HttpMethod.POST.name), permitAll)
            }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<FarmcontrolApplication>(*args)
}
