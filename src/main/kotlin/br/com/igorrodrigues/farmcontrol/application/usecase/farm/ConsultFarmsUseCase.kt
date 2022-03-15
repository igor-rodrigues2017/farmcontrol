package br.com.igorrodrigues.farmcontrol.application.usecase.farm

import br.com.igorrodrigues.farmcontrol.domain.model.farm.AllFarm
import org.springframework.stereotype.Service

@Service
class ConsultFarmsUseCase(private val allFarm: AllFarm) {

    fun existences(): List<FarmDto> {
        return allFarm.existing().map {
            FarmDto.convertFrom(it)
        }
    }

}
