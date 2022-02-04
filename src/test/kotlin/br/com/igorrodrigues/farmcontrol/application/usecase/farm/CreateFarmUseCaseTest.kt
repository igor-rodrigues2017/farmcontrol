package br.com.igorrodrigues.farmcontrol.application.usecase.farm

import br.com.igorrodrigues.farmcontrol.domain.model.farm.AllFarm
import br.com.igorrodrigues.farmcontrol.domain.model.farm.Farm
import br.com.igorrodrigues.farmcontrol.domain.model.farm.FarmLocation
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks

internal class CreateFarmUseCaseTest {

    @Mock
    private lateinit var allFarm: AllFarm

    @BeforeEach
    internal fun setUp() {
        openMocks(this)
        whenever(allFarm.save(aFarm())).thenReturn(aFarm().copy(id = 1))
    }

    @Test
    internal fun `should persist a new farm`() {
        val idCreated = CreateFarmUseCase(allFarm).create(aFarmDto())
        verify(allFarm).save(aFarm())
        assertEquals(1, idCreated)
    }

    private fun aFarmDto() =
        FarmDto(
            "Farm One",
            FarmLocationDto(
                "Floresta Azul",
                "BA",
                "Brazil"
            )
        )

    private fun aFarm() = Farm(
            farmName = "Farm One",
            location = FarmLocation(
                    city = "Floresta Azul",
                    state = "BA",
                    country = "Brazil"))
}
