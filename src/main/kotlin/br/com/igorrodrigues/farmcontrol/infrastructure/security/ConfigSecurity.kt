package br.com.igorrodrigues.farmcontrol.infrastructure.security

import br.com.igorrodrigues.farmcontrol.domain.model.AllUser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod.POST
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class ConfigSecurity(
        private val authService: AuthenticationService,
        private val tokenService: TokenService,
        private val allUser: AllUser
): WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity?) {
        http {
            httpBasic {}
            csrf { disable() }
            sessionManagement { sessionCreationPolicy = STATELESS }
            authorizeRequests {
                authorize(POST,"/signup", permitAll)
                authorize(POST,"/auth", permitAll)
                authorize(anyRequest, authenticated)
            }
            addFilterBefore(AuthenticationTokenFilter(tokenService, allUser), UsernamePasswordAuthenticationFilter::class.java)
        }
    }

    @Bean
    override fun authenticationManager(): AuthenticationManager {
        return super.authenticationManager()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.let { it.userDetailsService(authService).passwordEncoder(BCryptPasswordEncoder()) }
    }

}