package br.com.igorrodrigues.farmcontrol.controller

import br.com.igorrodrigues.farmcontrol.domain.model.User
import br.com.igorrodrigues.farmcontrol.domain.useCase.CreateUserUseCase
import br.com.igorrodrigues.farmcontrol.domain.useCase.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController {

    @Autowired
    private lateinit var createUserUseCase: CreateUserUseCase

    @PostMapping("/signin")
    fun signin(@RequestBody userDto: UserDto): ResponseEntity<User> {
        return ResponseEntity.status(CREATED).body(createUserUseCase.create(userDto))
    }
}