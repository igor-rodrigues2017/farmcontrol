package br.com.igorrodrigues.farmcontrol.controller.farm

import br.com.igorrodrigues.farmcontrol.domain.usecase.farm.CreateFarmUseCase
import br.com.igorrodrigues.farmcontrol.domain.usecase.farm.FarmDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("farms")
class FarmController(private val createFarmUseCase: CreateFarmUseCase) {

    @PostMapping("/add")
    fun addFarm(@RequestBody farmDto: FarmDto): ResponseEntity<String> {
        return createFarmUseCase.create(farmDto).let { ResponseEntity
                .created(URI.create("/farms/$it"))
                .build() }
    }
}