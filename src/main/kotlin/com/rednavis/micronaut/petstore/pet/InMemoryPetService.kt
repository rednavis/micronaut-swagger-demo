package com.rednavis.micronaut.petstore.pet

import com.rednavis.micronaut.petstore.dto.Pet
import com.rednavis.micronaut.petstore.dto.PetRequest
import com.rednavis.micronaut.petstore.dto.PetStatus
import com.rednavis.micronaut.petstore.exceptions.NotFoundException
import java.util.*
import java.util.concurrent.atomic.AtomicLong

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-20
 */
class InMemoryPetService(
    private val pets: MutableList<Pet>
) : PetService {

    private val currentMaxId: AtomicLong = (pets.asSequence()
        .map(Pet::id)
        .max() ?: 0)
        .let(::AtomicLong)

    private val petById: MutableMap<Long, Pet> = pets.associateByTo(mutableMapOf(), Pet::id)

    override suspend fun findAll(): List<Pet> = Collections.unmodifiableList(pets)

    override suspend fun findById(id: Long): Pet = petById[id] ?: throwPetNotFound(id)

    override suspend fun findAllByStatus(petStatus: PetStatus): List<Pet> = pets.filter { it.status == petStatus }

    override suspend fun deleteById(id: Long) {
        val pet = petById.remove(id) ?: throwPetNotFound(id)
        pets.remove(pet)
    }

    override suspend fun create(petRequest: PetRequest): Pet {
        val pet = petRequest.toPet(currentMaxId.incrementAndGet())
        petById[pet.id] = pet
        pets += pet
        return pet
    }

    override suspend fun update(id: Long, petRequest: PetRequest): Pet {
        val pet = findById(id)
        val index = pets.indexOf(pet)
        val updated = petRequest.toPet(id)
        pets[index] = updated
        petById[id] = updated
        return updated
    }

    private fun throwPetNotFound(id: Long): Nothing = throw NotFoundException("Pet(id=$id) not found")
}
