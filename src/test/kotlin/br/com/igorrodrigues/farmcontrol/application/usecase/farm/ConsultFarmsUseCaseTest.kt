package br.com.igorrodrigues.farmcontrol.application.usecase.farm

import br.com.igorrodrigues.farmcontrol.domain.model.farm.AllFarm
import br.com.igorrodrigues.farmcontrol.domain.model.farm.Farm
import br.com.igorrodrigues.farmcontrol.domain.model.farm.FarmLocation
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

internal class ConsultFarmsUseCaseTest : StringSpec({

    val aFarm = Farm(
        id = 1,
        farmName = "Fazenda 1",
        location = FarmLocation(
            city = "Floresta Azul",
            state = "BA",
            country = "Brasil"
        )
    )

    "should return all existent farms" {
        val allFarm = mockk<AllFarm>()
        every { allFarm.existing() } returns listOf(aFarm)

        ConsultFarmsUseCase(allFarm).existences() shouldBe listOf(FarmDto.convertFrom(aFarm))
    }

})