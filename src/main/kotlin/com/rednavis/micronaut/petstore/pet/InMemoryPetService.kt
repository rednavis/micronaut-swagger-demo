package com.rednavis.micronaut.petstore.pet

import com.rednavis.micronaut.petstore.dto.Pet
import com.rednavis.micronaut.petstore.dto.PetStatus
import com.rednavis.micronaut.petstore.util.toMaybe
import io.reactivex.Flowable
import io.reactivex.Maybe

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-20
 */
class InMemoryPetService(
    private val pets: MutableList<Pet>
) : PetService {

    private val petById: MutableMap<Long, Pet> = pets.asSequence()
        .filter { it.id != null }
        .associateByTo(mutableMapOf()) { it.id!! }

    override fun findAll(): Flowable<Pet> = Flowable.fromIterable(pets)

    override fun findById(id: Long): Maybe<Pet> = petById[id].toMaybe()

    override fun findAllByStatus(petStatus: PetStatus): Flowable<Pet> = Flowable.fromIterable(pets)
        .filter { it.status == petStatus }
}
