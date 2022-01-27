package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.user

import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import br.com.igorrodrigues.farmcontrol.domain.model.user.UserNotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

internal class AllUserAdapterTest {

    private val EMAIL_NONEXISTENT = "nonexistent@email.com"
    @Mock
    private lateinit var allUserRepository: AllUserRepository

    @BeforeEach
    internal fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    internal fun `should thrown UserNotFoundException when looking for an email nonexistent`() {
        `when`(allUserRepository.findByEmail(EMAIL_NONEXISTENT)).thenReturn(null)
        assertThrows(UserNotFoundException::class.java) {
            AllUserAdapter(allUserRepository).withEmail(EMAIL_NONEXISTENT)
        }
    }

    @Test
    internal fun `should return false when looking for an email nonexistent`() {
        `when`(allUserRepository.findByEmail(EMAIL_NONEXISTENT)).thenReturn(null)
        assertFalse(AllUserAdapter(allUserRepository).userAlreadyExist(EMAIL_NONEXISTENT))
    }

    @Test
    internal fun `should return true when looking for an email existent`() {
        val emailExistent = "existent@email.com"
        `when`(allUserRepository.findByEmail(emailExistent)).thenReturn(User())
        assertTrue(AllUserAdapter(allUserRepository).userAlreadyExist(emailExistent))
    }
}