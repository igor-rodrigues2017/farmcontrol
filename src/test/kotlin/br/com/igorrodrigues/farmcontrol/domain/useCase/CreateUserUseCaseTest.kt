package br.com.igorrodrigues.farmcontrol.domain.useCase

import br.com.igorrodrigues.farmcontrol.domain.model.AllUser
import br.com.igorrodrigues.farmcontrol.domain.model.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*

class CreateUserUseCaseTest {

    private val EMAIL = "user@teste.com"
    private val PASSWORD = "1234"
    private lateinit var allUser: AllUser

    @BeforeEach
    private fun init() {
        allUser = mock(AllUser::class.java)
    }

    @Test
    fun `should persist a new user`() {
        `when`(allUser.save(any())).thenReturn(User(
                id = 1,
                email = EMAIL,
                password = PASSWORD
        ))

        CreateUserUseCase(allUser).create(UserDto(
                EMAIL,
                PASSWORD
        ))

        verify(allUser).save(User(
                email = EMAIL,
                password = "1234"
        ))
    }
}