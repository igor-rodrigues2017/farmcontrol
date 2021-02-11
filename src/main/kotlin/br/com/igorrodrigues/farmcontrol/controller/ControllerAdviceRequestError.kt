package br.com.igorrodrigues.farmcontrol.controller

import br.com.igorrodrigues.farmcontrol.domain.usecase.user.CreateUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

@ControllerAdvice
class ControllerAdviceRequestError {

    @ExceptionHandler(value = [(CreateUserUseCase.UserAlreadyExistentException::class)])
    fun handleUserAlreadyExists(exception: CreateUserUseCase.UserAlreadyExistentException, request: WebRequest) =
            ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exception.message?.let { ErrorDetail(it) })
}

data class ErrorDetail(val message: String)