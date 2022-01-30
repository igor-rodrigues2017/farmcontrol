package br.com.igorrodrigues.farmcontrol.controller.auth

import br.com.igorrodrigues.farmcontrol.application.usecase.user.CreateUserUseCase
import br.com.igorrodrigues.farmcontrol.application.usecase.user.UserDto
import br.com.igorrodrigues.farmcontrol.infrastructure.security.TokenDto
import br.com.igorrodrigues.farmcontrol.infrastructure.security.TokenService
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val createUserUseCase: CreateUserUseCase,
    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService
) {

    @PostMapping("/signup")
    fun signin(@RequestBody userDto: UserDto): ResponseEntity<UserCreatedDto> {
        encryptPassword(userDto)
        return createUserUseCase.create(userDto).let {
            ResponseEntity
                .status(CREATED)
                .contentType(APPLICATION_JSON)
                //TODO: Should return location instead body ??
                .body(UserCreatedDto(id = it.id, email = it.email))
        }
    }

    private fun encryptPassword(userDto: UserDto) {
        userDto.password = BCryptPasswordEncoder().encode(userDto.password)
    }

    @GetMapping("/auth")
    fun authenticate(@RequestBody userDto: UserDto): ResponseEntity<TokenDto> {
        val authenticate = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                userDto.email,
                userDto.password
            )
        )
        return tokenService.generateToken(authenticate).let { ResponseEntity.status(OK).body(it) }
    }
}