package br.com.igorrodrigues.farmcontrol.controller

import br.com.igorrodrigues.farmcontrol.domain.model.User
import br.com.igorrodrigues.farmcontrol.domain.useCase.CreateUserUseCase
import br.com.igorrodrigues.farmcontrol.domain.useCase.UserDto
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.argumentCaptor
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
internal class AuthControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var createUserUseCase: CreateUserUseCase

    @Test
    internal fun `should save a user, return user created with status 201`() {
        val userDto = UserDto("teste@test.com", "1234")
        val user = User(id = 1, email = "teste@test.com")
        `when`(createUserUseCase.create(anyObject())).thenReturn(user)

        mockMvc.post("/signup") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(userDto)
            accept = APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            content { contentType(APPLICATION_JSON) }
            content { json(jacksonObjectMapper().writeValueAsString(user)) }
        }
        verify(createUserUseCase).create(argumentCaptor<UserDto>().capture())
    }

    @Test
    internal fun `should return a new token`() {
        val userDto = UserDto("teste@test.com", "1234")
        mockMvc.post("/auth") {
            contentType = APPLICATION_JSON
            content = jacksonObjectMapper().writeValueAsString(userDto)
            accept = APPLICATION_JSON
        }
    }

    private fun <T> anyObject(): T {
        any<T>()
        return uninitialized()
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> uninitialized(): T = null as T
}