package com.rednavis.micronaut.petstore.pet

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.rednavis.micronaut.petstore.dto.Category
import com.rednavis.micronaut.petstore.dto.Pet
import com.rednavis.micronaut.petstore.dto.PetStatus
import com.rednavis.micronaut.petstore.dto.Tag
import io.micronaut.context.annotation.Factory
import javax.inject.Singleton

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-20
 */
@Factory
class PetServiceFactory {

    data class JsonPet(
        val id: Long,
        val category: JsonCategory?,
        val name: String?,
        val photoUrls: List<String>?,
        val tags: List<JsonTag>?,
        val status: PetStatus
    ) {
        fun toPet(): Pet? {
            return Pet(
                id = id,
                category = category?.toCategory() ?: return null,
                name = name ?: return null,
                photoUrls = photoUrls ?: listOf(),
                tags = (tags?.asSequence() ?: sequenceOf())
                    .map(JsonTag::toTag)
                    .filterNotNull()
                    .toList(),
                status = status
            )
        }
    }

    data class JsonCategory(
        val id: Long,
        val name: String?
    ) {
        fun toCategory(): Category? {
            return Category(
                id = id,
                name = name ?: return null
            )
        }
    }

    data class JsonTag(
        val id: Long,
        val name: String?
    ) {
        fun toTag(): Tag? {
            return Tag(
                id = id,
                name = name ?: return null
            )
        }
    }

    @Singleton
    fun petService(objectMapper: ObjectMapper): PetService {
        val pets = sequenceOf("/pets.available.json", "/pets.pending.json", "/pets.sold.json")
            .flatMap { resourceName ->
                javaClass.getResourceAsStream(resourceName).use { inputStream ->
                    objectMapper.readValue<List<JsonPet>>(inputStream)
                }.asSequence()
            }
            .map(JsonPet::toPet)
            .filterNotNull()
            .toMutableList()
        return InMemoryPetService(pets)
    }
}
