package br.com.igorrodrigues.farmcontrol.infrastructure.persistence

import br.com.igorrodrigues.farmcontrol.domain.model.User
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager

@DataJpaTest
@AutoConfigureTestDatabase
class AllUserRepositoryTest @Autowired constructor(
        val entityManager: TestEntityManager,
        val allUserRepository: AllUserRepository
){
    @Test
    internal fun `when findByEmail then return User`() {
        val user = User(email = "test@test.com.br")
        entityManager.persist(user)
        val userFound = allUserRepository.findByEmail("test@test.com.br")
        assertThat(userFound, equalTo(user))
    }
}