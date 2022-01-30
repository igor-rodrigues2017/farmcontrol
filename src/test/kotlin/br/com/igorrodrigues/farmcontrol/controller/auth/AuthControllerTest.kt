package br.com.igorrodrigues.farmcontrol.controller.auth

import br.com.igorrodrigues.farmcontrol.application.usecase.user.CreateUserUseCase
import br.com.igorrodrigues.farmcontrol.application.usecase.user.UserDto
import br.com.igorrodrigues.farmcontrol.controller.ErrorDetail
import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import br.com.igorrodrigues.farmcontrol.infrastructure.security.TokenDto
import br.com.igorrodrigues.farmcontrol.infrastructure.security.TokenService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
internal class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var createUserUseCase: CreateUserUseCase

    @MockBean
    private lateinit var authenticationManager: AuthenticationManager

    @MockBean
    private lateinit var tokenService: TokenService

    @Test
    internal fun `should save a user and return user created with status 201`() {
        val userDto = UserDto("teste@test.com", "1234")
        val user = User(id = 1, email = "teste@test.com")
        whenever(createUserUseCase.create(anyObject())).thenReturn(user)

        mockMvc.post("/signup") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(userDto)
            accept = APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { contentType(APPLICATION_JSON) }
            content { json(jacksonObjectMapper().writeValueAsString(UserCreatedDto(id = user.id, email = user.email))) }
        }
        verify(createUserUseCase).create(argumentCaptor<UserDto>().capture())
    }

    @Test
    internal fun `should return status 422 when user already exists`() {
        val userDto = UserDto("existents@user.com", "1234")
        whenever(createUserUseCase.create(anyObject())).thenThrow(CreateUserUseCase.UserAlreadyExistentException())
        mockMvc.post("/signup") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(userDto)
            accept = APPLICATION_JSON
        }.andExpect {
            status { isUnprocessableEntity() }
            content { contentType(APPLICATION_JSON) }
            content { json(jacksonObjectMapper().writeValueAsString(ErrorDetail("User already existent"))) }
        }
    }

    @Test
    internal fun `should return a new token`() {
        val userDto = UserDto("teste@test.com", "1234")
        val authentication = mock(Authentication::class.java)
        whenever(authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
                userDto.email,
                userDto.password
        ))).thenReturn(authentication)
        val tokenDto = TokenDto("Token Válido")
        `when`(tokenService.generateToken(authentication)).thenReturn(tokenDto)
        mockMvc.get("/auth") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(userDto)
            accept = APPLICATION_JSON
        }.andExpect {
            status { isOk() }
            content { json(jacksonObjectMapper().writeValueAsString(tokenDto)) }
        }
    }

    private fun <T> anyObject(): T {
        any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T
}