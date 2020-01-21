package com.rednavis.micronaut.petstore.pet

import com.rednavis.micronaut.petstore.dto.Pet
import com.rednavis.micronaut.petstore.dto.PetStatus
import io.reactivex.Flowable

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

    override fun findAllByStatus(petStatus: PetStatus): Flowable<Pet> = Flowable.fromIterable(pets)
        .filter { it.status == petStatus }
}
