package br.com.igorrodrigues.farmcontrol.controller.farm

import br.com.igorrodrigues.farmcontrol.application.usecase.farm.ConsultFarmsUseCase
import br.com.igorrodrigues.farmcontrol.application.usecase.farm.CreateFarmUseCase
import br.com.igorrodrigues.farmcontrol.application.usecase.farm.FarmDto
import br.com.igorrodrigues.farmcontrol.application.usecase.farm.FarmLocationDto
import br.com.igorrodrigues.farmcontrol.domain.model.user.AllUser
import br.com.igorrodrigues.farmcontrol.domain.model.user.User
import br.com.igorrodrigues.farmcontrol.infrastructure.security.Credentials
import br.com.igorrodrigues.farmcontrol.infrastructure.security.TokenService
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.security.core.Authentication
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class, MockitoExtension::class)
internal class FarmControllerTest {

    val FARM_ID_CREATED = 1L

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var tokenService: TokenService

    @MockBean
    private lateinit var authenticate: Authentication

    @MockBean
    private lateinit var allUser: AllUser

    @MockBean
    private lateinit var createFarmUseCase: CreateFarmUseCase

    @MockBean
    private lateinit var consultFarmsUseCase: ConsultFarmsUseCase

    @BeforeEach
    internal fun setup() {
        whenever(allUser.withEmail("user@user.com")).thenReturn(aUser())
        whenever(authenticate.principal).thenReturn(Credentials(aUser()))
        whenever(createFarmUseCase.create(aFarm())).thenReturn(FARM_ID_CREATED)
        whenever(consultFarmsUseCase.existences()).thenReturn(listOf(aFarm()))
    }

    @Test
    internal fun `should create a new farm`() {
        mockMvc.post("/farms/add") {
            header("Authorization", tokenService.generateToken(authenticate).toString())
            contentType = APPLICATION_JSON
            content = aJsonFarm()
            accept = APPLICATION_JSON
        }.andExpect {
            status { isCreated() }
            header { string("Location", "/farms/$FARM_ID_CREATED") }
        }
        verify(createFarmUseCase).create(aFarm())
    }

    @Test
    internal fun `should consult all farms`() {
        mockMvc.get("/farms") {
            header("Authorization", tokenService.generateToken(authenticate).toString())
        }.andExpect {
            status { isOk() }
            content { contentType(APPLICATION_JSON) }
            content { json(aJsonFarmList()) }
        }
        verify(consultFarmsUseCase).existences()
    }

    private fun aJsonFarm() = jacksonObjectMapper().writeValueAsString(aFarm())

    private fun aJsonFarmList() = jacksonObjectMapper().writeValueAsString(listOf(aFarm()))

    private fun aFarm() = FarmDto(
        "Farm One",
        FarmLocationDto(
            city = "Floresta Azul",
            state = "BA",
            country = "Brazil"
        )
    )

    private fun aUser() = User(
        email = "user@user.com",
        password = "1234",
        id = 1
    )
}