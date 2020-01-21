package com.rednavis.micronaut.petstore.pet

import com.rednavis.micronaut.petstore.dto.Pet
import com.rednavis.micronaut.petstore.dto.PetStatus
import io.reactivex.Flowable

interface PetService {

    fun findAllByStatus(petStatus: PetStatus): Flowable<Pet>

}
