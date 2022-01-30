package br.com.igorrodrigues.farmcontrol.infrastructure.persistence.farm

import org.springframework.data.jpa.repository.JpaRepository

interface AllFarmRepository : JpaRepository<FarmData, Long>