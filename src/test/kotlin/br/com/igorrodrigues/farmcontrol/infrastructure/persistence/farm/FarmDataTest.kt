package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.farm

import br.com.igorrodrigues.farmcontrol.domain.model.farm.Farm
import br.com.igorrodrigues.farmcontrol.domain.model.farm.FarmLocation
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

private const val CITY = "City"

private const val STATE = "State"

private const val COUNTRY = "Country"

private const val FARM_NAME = "Farm name"

class FarmDataTest : StringSpec({

    val aFarmLocation = FarmLocation(
        city = CITY,
        state = STATE,
        country = COUNTRY
    )

    val aFarm = Farm(
        farmName = FARM_NAME,
        location = aFarmLocation
    )

    val aFarmData = FarmData(
        farmName = FARM_NAME,
        location = FarmLocationData(
            city = CITY,
            state = STATE,
            country = COUNTRY
        )
    )
    "Should return a FarmData from a Farm" {
        FarmData.from(aFarm) shouldBe aFarmData
    }

    "Should return a Farm from a FarmData" {
        aFarmData.toFarm() shouldBe aFarm
    }
})
