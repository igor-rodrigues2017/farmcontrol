package br.com.igorrodrigues.farmcontrol.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class LoginController {

    @GetMapping("/")
    fun teste() ="Hello World, welcome!"
}