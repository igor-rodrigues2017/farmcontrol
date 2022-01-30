package br.com.igorrodrigues.farmcontrol.domain.model.farm

data class Farm(
    val id: Long = 0,
    val farmName: String,
    val location: FarmLocation
)

data class FarmLocation(
    val city: String,
    val state: String,
    val country: String,
)
