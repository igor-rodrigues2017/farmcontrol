package br.com.igorrodrigues.farmcontrol.domain.usecase.user

import br.com.igorrodrigues.farmcontrol.domain.model.user.AllUser
import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import br.com.igorrodrigues.farmcontrol.domain.model.user.UserNotFoundException
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.openMocks

class CreateUserUseCaseTest {

    private val EMAIL = "user@teste.com"
    private val PASSWORD = "1234"

    @Mock
    private lateinit var allUser: AllUser

    @Mock
    private lateinit var tenantService: TenantService

    private lateinit var useCase: CreateUserUseCase

    @BeforeEach
    private fun init() {
        openMocks(this)
        whenever(allUser.withEmail(EMAIL)).thenThrow(UserNotFoundException())
        whenever(allUser.save(User(email = EMAIL, password = PASSWORD))).thenReturn(User(
                id = 1,
                email = EMAIL,
                password = PASSWORD
        ))
        useCase = CreateUserUseCase(allUser, tenantService)
    }

    @Test
    fun `should persist a new user and create a new schema for this user`() {
        useCase.create(UserDto(
                EMAIL,
                PASSWORD
        ))

        verify(allUser).save(User(
                email = EMAIL,
                password = "1234"
        ))
        verify(tenantService).initDatabase(EMAIL)
    }

    @Test
    internal fun `should not persist a new user when user already exist`() {
        val emailAlreadyExistents = "existent@user.com"
        whenever(allUser.userAlreadyExist(emailAlreadyExistents)).thenReturn(true)
        assertThrows(CreateUserUseCase.UserAlreadyExistentException::class.java) {
            useCase.create(UserDto(emailAlreadyExistents, PASSWORD))
        }
    }
}