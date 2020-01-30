package com.rednavis.micronaut.petstore

import com.rednavis.micronaut.petstore.dto.*
import com.rednavis.micronaut.petstore.exceptions.NotFoundException
import com.rednavis.micronaut.petstore.pet.PetService
import io.kotlintest.matchers.collections.shouldBeEmpty
import io.kotlintest.shouldBe
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.defaultSerializer
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import io.micronaut.test.annotation.MockBean
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
class PetControllerTest {

    @Inject
    lateinit var embeddedServer: EmbeddedServer

    @Inject
    lateinit var petService: PetService

    private val pet = Pet(
        id = 1,
        category = Category(
            id = 1,
            name = "category1"
        ),
        name = "pet1",
        photoUrls = emptyList(),
        tags = emptyList(),
        status = PetStatus.AVAILABLE
    )

    private val petRequest = PetRequest(
        category = Category(
            id = 1,
            name = "category1"
        ),
        name = "pet1",
        photoUrls = emptyList(),
        tags = emptyList(),
        status = PetStatus.AVAILABLE
    )

    private val client: HttpClient = HttpClient {
        expectSuccess = false
        install(JsonFeature)
    }

    private suspend fun getPets(): List<Pet> = client.get("${embeddedServer.url}/pet")

    private suspend inline fun <reified T> HttpClient.getPet(id: Int): T = get("${embeddedServer.url}/pet/$id")

    private suspend inline fun <reified T> HttpClient.deletePet(id: Int): T = delete("${embeddedServer.url}/pet/$id")

    private suspend inline fun <reified T> HttpClient.updatePet(id: Int, petRequest: PetRequest): T = put("${embeddedServer.url}/pet/$id") {
        body = defaultSerializer().write(petRequest)
    }

    private suspend fun createPet(petRequest: PetRequest): Pet = client.post("${embeddedServer.url}/pet") {
        body = defaultSerializer().write(petRequest)
    }

    @Test
    internal fun `get empty list of pets`() = runBlocking {
        coEvery { petService.findAll() } returns emptyList()
        getPets().shouldBeEmpty()
    }

    @Test
    internal fun `get pet list with single pet`() = runBlocking {
        coEvery { petService.findAll() } returns listOf(pet)
        getPets() shouldBe listOf(pet)
    }

    @Test
    internal fun `get non-existing pet`() = runBlocking {
        coEvery { petService.findById(1) } throws NotFoundException("pet not found")
        client.getPet<Problem>(1) shouldBe Problem(status = 404, details = "pet not found")
    }

    @Test
    internal fun `get pet by id`() = runBlocking {
        coEvery { petService.findById(1) } returns pet
        client.getPet<Pet>(1) shouldBe pet
    }

    @Test
    internal fun `create pet`() = runBlocking {
        coEvery { petService.create(petRequest) } returns pet
        createPet(petRequest) shouldBe pet
    }

    @Test
    internal fun `delete non-existing pet`() = runBlocking {
        coEvery { petService.deleteById(1) } throws NotFoundException("pet not found")
        client.deletePet<Problem>(1) shouldBe Problem(status = 404, details = "pet not found")
    }

    @Test
    internal fun `delete pet`() = runBlocking {
        coEvery { petService.deleteById(1) } returns Unit
        client.deletePet<Unit>(1) shouldBe Unit
    }

    @Test
    internal fun `update pet`() = runBlocking {
        coEvery { petService.update(1, petRequest) } returns pet
        client.updatePet<Pet>(1, petRequest) shouldBe pet
    }

    @Test
    internal fun `update non-existing pet`() = runBlocking {
        coEvery { petService.update(1, petRequest) } throws NotFoundException("pet not found")
        client.updatePet<Problem>(1, petRequest) shouldBe Problem(status = 404, details = "pet not found")
    }

    @MockBean(PetService::class)
    fun petService(): PetService = mockk()
}
