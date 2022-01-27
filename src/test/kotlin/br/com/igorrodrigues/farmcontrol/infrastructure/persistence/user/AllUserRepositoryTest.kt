package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.user

import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import br.com.igorrodrigues.farmcontrol.infrastructure.persistence.user.AllUserRepository
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo
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
    //@Test
    //TODO: Make this test run...
    internal fun `when findByEmail then return User`() {
        val user = User(email = "test@test.com.br")
        entityManager.persist(user)
        val userFound = allUserRepository.findByEmail("test@test.com.br")
        assertThat(userFound, equalTo(user))
    }
}