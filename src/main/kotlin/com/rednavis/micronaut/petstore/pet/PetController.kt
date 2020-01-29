package com.rednavis.micronaut.petstore.pet

import com.rednavis.micronaut.petstore.dto.Pet
import com.rednavis.micronaut.petstore.dto.PetRequest
import com.rednavis.micronaut.petstore.dto.PetStatus
import io.micronaut.http.annotation.*
import javax.inject.Inject

@Controller("/pet")
class PetController @Inject constructor(
    private val petService: PetService
) {

    @Get
    suspend fun findAll(): List<Pet> = petService.findAll()

    @Get("/{id}")
    suspend fun findById(@PathVariable id: Long): Pet = petService.findById(id)

    @Get("/findByStatus")
    suspend fun findByStatus(@QueryValue status: PetStatus): List<Pet> = petService.findAllByStatus(status)

    @Post
    suspend fun create(@Body pet: PetRequest): Pet = petService.create(pet)

    @Put("/{id}")
    suspend fun update(@PathVariable id: Long, @Body petRequest: PetRequest): Pet = petService.update(id, petRequest)

    @Delete("/{id}")
    suspend fun deleteById(@PathVariable id: Long) = petService.deleteById(id)
}
