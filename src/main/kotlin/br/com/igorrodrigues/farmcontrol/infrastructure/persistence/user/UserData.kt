package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.user

import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import javax.persistence.*

@Entity
@Table(name = "users")
data class UserData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val email: String = "",
    val password: String = ""
) {
    fun toUser() = User(id = this.id, email = this.email, password = this.password)

    companion object {
        fun from(user: User) = UserData(email = user.email, password = user.password)
    }
}
