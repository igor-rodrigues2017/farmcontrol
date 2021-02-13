package br.com.igorrodrigues.farmcontrol.infrastructure.persistence

import br.com.igorrodrigues.farmcontrol.domain.model.farm.AllFarm
import br.com.igorrodrigues.farmcontrol.domain.model.farm.Farm
import org.springframework.stereotype.Repository

@Repository
class AllFarmAdapter(private val allFarmRepository: AllFarmRepository): AllFarm {
    override fun save(farm: Farm) = allFarmRepository.save(farm)
}