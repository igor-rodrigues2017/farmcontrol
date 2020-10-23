package br.com.igorrodrigues.farmcontrol

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.web.config.EnableSpringDataWebSupport

@SpringBootApplication
@EnableSpringDataWebSupport
class FarmcontrolApplication

fun main(args: Array<String>) {
    runApplication<FarmcontrolApplication>(*args)
}
