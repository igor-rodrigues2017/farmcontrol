package br.com.igorrodrigues.farmcontrol.domain.model.farm

import javax.persistence.*

@Entity
@Table(name = "farm")
data class Farm(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0,
        val farmName: String,
        @Embedded val location: FarmLocation?
)

@Embeddable
data class FarmLocation(
        val city: String,
        val state: String,
        val country: String,
)
