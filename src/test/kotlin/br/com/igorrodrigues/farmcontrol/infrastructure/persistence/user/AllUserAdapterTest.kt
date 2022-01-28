package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.user

import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import br.com.igorrodrigues.farmcontrol.domain.model.user.UserNotFoundException
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

const val EMAIL_NONEXISTENT = "nonexistent@email.com"

internal class AllUserAdapterTest : StringSpec({

    val allUserRepository = mockk<AllUserRepository>()

    "should thrown UserNotFoundException when looking for an email nonexistent" {
        every { allUserRepository.findByEmail(EMAIL_NONEXISTENT) } returns null
        shouldThrow<UserNotFoundException> {
            AllUserAdapter(allUserRepository).withEmail(EMAIL_NONEXISTENT)
        }
    }

    "should return false when looking for an email nonexistent" {
        every { allUserRepository.findByEmail(EMAIL_NONEXISTENT) } returns null
        AllUserAdapter(allUserRepository).userAlreadyExist(EMAIL_NONEXISTENT) shouldBe false
    }

    "should return true when looking for an email existent" {
        every { allUserRepository.findByEmail("existent@email.com") } returns UserData()
        AllUserAdapter(allUserRepository).userAlreadyExist("existent@email.com") shouldBe true
    }
})