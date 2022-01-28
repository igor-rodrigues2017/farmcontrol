package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.user

import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class UserDataTest : StringSpec({

    "should create a UserData from User" {
        val user = User(email = "iguin@teste.com", password = "*12345*")
        UserData.from(user) shouldBe UserData(email = user.email, password = user.password)
    }

    "should create a User from UserData" {
        UserData(
            id = 10,
            email = "iguin@teste.com",
            password = "*12345*"
        ).toUser() shouldBe User(
            id = 10,
            email = "iguin@teste.com",
            password = "*12345*"
        )
    }

})
