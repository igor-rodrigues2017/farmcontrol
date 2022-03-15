package br.com.igorrodrigues.farmcontrol.controller.farm

import br.com.igorrodrigues.farmcontrol.application.usecase.farm.ConsultFarmsUseCase
import br.com.igorrodrigues.farmcontrol.application.usecase.farm.CreateFarmUseCase
import br.com.igorrodrigues.farmcontrol.application.usecase.farm.FarmDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("farms")
class FarmController(
    private val createFarmUseCase: CreateFarmUseCase,
    private val consultFarmsUseCase: ConsultFarmsUseCase,
) {

    @PostMapping("/add")
    fun addFarm(@RequestBody farmDto: FarmDto): ResponseEntity<String> {
        return createFarmUseCase.create(farmDto).let {
            ResponseEntity
                .created(URI.create("/farms/$it"))
                .build()
        }
    }

    @GetMapping
    fun consultFarms(): ResponseEntity<List<FarmDto>> {
        return consultFarmsUseCase.existences().let {
            ResponseEntity.ok(it)
        }
    }
}