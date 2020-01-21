package com.rednavis.micronaut.petstore.pet

import com.rednavis.micronaut.petstore.dto.Pet
import com.rednavis.micronaut.petstore.dto.PetStatus
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.reactivex.Flowable
import javax.inject.Inject

@Controller("/pet")
class PetController @Inject constructor(
    private val petService: PetService
) {

    @Get("/findByStatus")
    fun index(@QueryValue status: PetStatus): Flowable<Pet> {
        return petService.findAllByStatus(status)
    }
}
