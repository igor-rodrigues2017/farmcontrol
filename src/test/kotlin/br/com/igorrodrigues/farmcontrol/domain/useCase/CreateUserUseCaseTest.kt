package br.com.igorrodrigues.farmcontrol.domain.useCase

import br.com.igorrodrigues.farmcontrol.domain.model.AllUser
import br.com.igorrodrigues.farmcontrol.domain.model.User
import br.com.igorrodrigues.farmcontrol.domain.model.UserNotFoundException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.openMocks

class CreateUserUseCaseTest {

    private val EMAIL = "user@teste.com"
    private val PASSWORD = "1234"

    @Mock
    private lateinit var allUser: AllUser

    @BeforeEach
    private fun init() {
        openMocks(this)
        `when`(allUser.withEmail(EMAIL)).thenThrow(UserNotFoundException())
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

    @Test
    internal fun `should not persist a new user when user already exist`() {
        val emailAlreadyExistents = "existent@user.com"
        `when`(allUser.withEmail(emailAlreadyExistents)).thenReturn(User(1, emailAlreadyExistents, PASSWORD))
        assertThrows(CreateUserUseCase.UserAlreadyExistentException::class.java) {
            CreateUserUseCase(allUser).create(UserDto(emailAlreadyExistents, PASSWORD))
        }
    }
}