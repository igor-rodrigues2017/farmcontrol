package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.farm

import br.com.igorrodrigues.farmcontrol.domain.model.farm.Farm
import org.springframework.data.jpa.repository.JpaRepository

interface AllFarmRepository: JpaRepository<Farm, Long>