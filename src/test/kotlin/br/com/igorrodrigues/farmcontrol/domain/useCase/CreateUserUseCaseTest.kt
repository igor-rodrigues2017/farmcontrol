package br.com.igorrodrigues.farmcontrol.domain.useCase

import br.com.igorrodrigues.farmcontrol.domain.model.AllUser
import br.com.igorrodrigues.farmcontrol.domain.model.User
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class CreateUserUseCaseTest {

    private val EMAIL = "user@teste.com"
    private val PASSWORD = "1234"

    @Mock
    private lateinit var allUser: AllUser

    @BeforeEach
    private fun init() {
        MockitoAnnotations.openMocks(this)
        `when`(allUser.save(User(email = EMAIL, password = PASSWORD))).thenReturn(User(
                id = 1,
                email = EMAIL,
                password = PASSWORD
        ))
    }

    @Test
    fun `should persist a new user`() {
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