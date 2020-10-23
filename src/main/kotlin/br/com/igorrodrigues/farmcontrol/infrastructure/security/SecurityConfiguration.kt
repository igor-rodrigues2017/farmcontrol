package br.com.igorrodrigues.farmcontrol.infrastructure.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@EnableWebSecurity
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

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

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder())
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}