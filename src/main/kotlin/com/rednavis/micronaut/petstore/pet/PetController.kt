package com.rednavis.micronaut.petstore.pet

import com.rednavis.micronaut.petstore.dto.Pet
import com.rednavis.micronaut.petstore.dto.PetStatus
import io.micronaut.http.annotation.*
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import javax.inject.Inject

@Controller("/pet")
class PetController @Inject constructor(
    private val petService: PetService
) {

    @Get
    fun findAll(): Flowable<Pet> = petService.findAll()

    @Get("/{petId}")
    fun findById(@PathVariable petId: Long): Maybe<Pet> = petService.findById(petId)

    @Get("/findByStatus")
    fun findByStatus(@QueryValue status: PetStatus): Flowable<Pet> = petService.findAllByStatus(status)

    @Post
    fun create(@Body pet: Pet): Single<Pet> = petService.create(pet)

    @Put("/{petId}")
    fun update(@PathVariable petId: Long, @Body pet: Pet): Single<Pet> = petService.update(pet.copy(id = petId))

    @Delete("/{petId}")
    fun deleteById(@PathVariable petId: Long): Completable = petService.deleteById(petId)
}
