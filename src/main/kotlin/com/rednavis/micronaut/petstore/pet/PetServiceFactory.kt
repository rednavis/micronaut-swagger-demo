package com.rednavis.micronaut.petstore.pet

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rednavis.micronaut.petstore.dto.Pet
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-20
 */
@Factory
class PetServiceFactory {

    @Singleton
    fun petService(objectMapper: ObjectMapper): PetService {
        val pets = sequenceOf("/pets.available.json", "/pets.pending.json", "/pets.sold.json")
            .flatMap { resourceName ->
                javaClass.getResourceAsStream(resourceName).use { inputStream ->
                    objectMapper.readValue<List<Pet>>(inputStream)
                }.asSequence()
            }.toCollection(arrayListOf())
        return InMemoryPetService(ArrayList(pets))
    }
}
