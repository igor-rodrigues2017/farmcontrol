package br.com.igorrodrigues.farmcontrol.domain.usecase.farm

import br.com.igorrodrigues.farmcontrol.domain.model.farm.Farm
import br.com.igorrodrigues.farmcontrol.domain.model.farm.FarmLocation

data class FarmDto(
        val farmName: String,
        val location: FarmLocationDto,
) {
    fun convertToFarm() =
            Farm(
                    farmName = farmName,
                    location = FarmLocation(
                            city = location.city,
                            state = location.state,
                            country = location.country))


}
data class FarmLocationDto(
        val city: String,
        val state: String,
        val country: String,
)
