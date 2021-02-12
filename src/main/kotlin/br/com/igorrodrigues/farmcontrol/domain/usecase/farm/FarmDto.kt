package br.com.igorrodrigues.farmcontrol.domain.usecase.farm

data class FarmDto(
        val farmName: String,
        val location: FarmLocation,
) {
    data class FarmLocation(
            val city: String,
            val state: String,
            val country: String,
    )
}