package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.user

import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

private const val EMAIL = "iguin@teste.com"

private const val PASSWORD = "*12345*"

val aUser = User(email = EMAIL, password = PASSWORD)

val aUserData = UserData(email = EMAIL, password = PASSWORD)

class UserDataTest : StringSpec({

    "should create a UserData from User" {
        UserData.from(aUser) shouldBe aUserData
    }

    "should create a User from UserData" {
        aUserData.toUser() shouldBe aUser
    }

})
