package br.com.igorrodrigues.farmcontrol.application.usecase.farm

import br.com.igorrodrigues.farmcontrol.domain.model.farm.AllFarm
import org.springframework.stereotype.Service

@Service
class CreateFarmUseCase(private val allFarm: AllFarm) {

    fun create(farmDto: FarmDto): Long =
        allFarm.save(farmDto.convertToFarm()).run { id }

}
