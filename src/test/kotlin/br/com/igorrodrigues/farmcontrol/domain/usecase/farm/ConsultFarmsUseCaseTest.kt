package br.com.igorrodrigues.farmcontrol.domain.usecase.farm

import br.com.igorrodrigues.farmcontrol.domain.model.farm.AllFarm
import br.com.igorrodrigues.farmcontrol.domain.model.farm.Farm
import br.com.igorrodrigues.farmcontrol.domain.model.farm.FarmLocation
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertIterableEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.openMocks

internal class ConsultFarmsUseCaseTest {

    @Mock private lateinit var allFarm: AllFarm

    @BeforeEach
    internal fun setUp() {
        openMocks(this)
    }

    @Test
    internal fun should_return_all_existents_farms() {
        whenever(allFarm.existing()).thenReturn(listOf(aFarm()))

        val farmExisting = ConsultFarmsUseCase(allFarm).existing()

        assertIterableEquals(listOf(FarmDto.convertFrom(aFarm())), farmExisting)
    }

    private fun aFarm() = Farm(id = 1,
            farmName = "Fazenda 1",
            location = FarmLocation(city = "Floresta Azul",
                    state = "BA",
                    country = "Brasil"))

}