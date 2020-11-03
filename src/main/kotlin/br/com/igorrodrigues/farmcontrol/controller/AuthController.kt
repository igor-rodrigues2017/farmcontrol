package br.com.igorrodrigues.farmcontrol.controller

import br.com.igorrodrigues.farmcontrol.domain.model.User
import br.com.igorrodrigues.farmcontrol.domain.useCase.CreateUserUseCase
import br.com.igorrodrigues.farmcontrol.domain.useCase.UserDto
import br.com.igorrodrigues.farmcontrol.infrastructure.security.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @Autowired
    private lateinit var createUserUseCase: CreateUserUseCase
    @Autowired
    private lateinit var authenticationManager: AuthenticationManager
    @Autowired
    private lateinit var tokenService: TokenService

    @PostMapping("/signup")
    fun signin(@RequestBody userDto: UserDto): ResponseEntity<User> {
        userDto.password = BCryptPasswordEncoder().encode(userDto.password)
        return createUserUseCase.create(userDto).let { ResponseEntity.status(CREATED).body(it) }
    }

    @PostMapping("/auth")
    fun authenticate(@RequestBody userDto: UserDto): ResponseEntity<String> {
        val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userDto.email, userDto.password)
        val authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken)
        val token = tokenService.generateToken(authenticate)
        return ResponseEntity.status(OK).body(token)
    }
}