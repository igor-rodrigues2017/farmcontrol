package br.com.igorrodrigues.farmcontrol.infrastructure.persistence

import br.com.igorrodrigues.farmcontrol.domain.model.UserNotFoundException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

internal class AllUserAdapterTest {

    @Mock
    private lateinit var allUserRepository: AllUserRepository

    @BeforeEach
    internal fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    internal fun `should thrown UserNotFoundException when looking for an email nonexistent`() {
        `when`(allUserRepository.findByEmail("nonexistent@email.com")).thenReturn(null)
        assertThrows(UserNotFoundException::class.java) {
            AllUserAdapter(allUserRepository).withEmail("nonexistent@email.com")
        }
    }
}