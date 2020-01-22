package com.rednavis.micronaut.petstore.pet

import com.rednavis.micronaut.petstore.dto.Pet
import com.rednavis.micronaut.petstore.dto.PetStatus
import com.rednavis.micronaut.petstore.exceptions.NotFoundException
import com.rednavis.micronaut.petstore.util.toMaybe
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import java.lang.IllegalArgumentException
import java.util.concurrent.atomic.AtomicLong

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-20
 */
class InMemoryPetService(
    private val pets: MutableList<Pet>
) : PetService {

    private val nextId: AtomicLong = (pets.asSequence()
        .filter { it.id != null }
        .map { it.id!! }
        .max() ?: 0)
        .let { AtomicLong(it + 1) }

    private val petById: MutableMap<Long, Pet> = pets.asSequence()
        .filter { it.id != null }
        .associateByTo(mutableMapOf()) { it.id!! }

    override fun findAll(): Flowable<Pet> = Flowable.fromIterable(pets)

    override fun findById(id: Long): Maybe<Pet> = petById[id].toMaybe()

    override fun findAllByStatus(petStatus: PetStatus): Flowable<Pet> = Flowable.fromIterable(pets)
        .filter { it.status == petStatus }

    override fun deleteById(id: Long): Completable {
        val index: Int = pets.indexOfFirst { it.id == id }
        if (index == -1) {
            return Completable.error(NotFoundException("Pet with id $id not found"))
        }
        pets.removeAt(index)
        petById.remove(id)
        return Completable.complete()
    }

    override fun create(pet: Pet): Single<Pet> {
        if (pet.id == null) {
            val id = nextId.getAndIncrement()
            val newPet = pet.copy(id = id)
            petById[id] = newPet
            pets.add(newPet)
            return Single.just(newPet)
        }

        return update(pet)
    }

    override fun update(pet: Pet): Single<Pet> {
        pet.id ?: return Single.error(IllegalArgumentException("Pet id is null"))

        val index = pets.indexOfFirst { it.id == pet.id }

        if (index == -1) {
            return Single.error(NotFoundException("Pet with id ${pet.id} not found"))
        }

        pets[index] = pet
        petById[pet.id] = pet

        return Single.just(pet)
    }
}
