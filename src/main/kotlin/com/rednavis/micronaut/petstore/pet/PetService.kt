package com.rednavis.micronaut.petstore.pet

import com.rednavis.micronaut.petstore.dto.Pet
import com.rednavis.micronaut.petstore.dto.PetStatus
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface PetService {

    fun findById(id: Long): Maybe<Pet>

    fun findAll(): Flowable<Pet>

    fun findAllByStatus(petStatus: PetStatus): Flowable<Pet>

    fun deleteById(id: Long): Completable

    fun create(pet: Pet): Single<Pet>

    fun update(pet: Pet): Single<Pet>
}
