package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.farm

import br.com.igorrodrigues.farmcontrol.domain.model.farm.Farm
import br.com.igorrodrigues.farmcontrol.domain.model.farm.FarmLocation
import javax.persistence.*

@Entity
@Table(name = "farm")
data class FarmData(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    val farmName: String,
    @Embedded val location: FarmLocationData
) {
    fun toFarm() = Farm(
        farmName = this.farmName, location = FarmLocation(
            city = location.city,
            state = location.state,
            country = location.country
        )
    )

    companion object {
        fun from(farm: Farm): FarmData =
            FarmData(
                farmName = farm.farmName,
                location = FarmLocationData(
                    city = farm.location.city,
                    state = farm.location.state,
                    country = farm.location.country
                )
            )
    }
}

@Embeddable
data class FarmLocationData(
    val city: String,
    val state: String,
    val country: String,
)