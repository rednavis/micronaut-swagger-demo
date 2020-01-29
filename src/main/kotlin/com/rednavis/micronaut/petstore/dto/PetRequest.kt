package com.rednavis.micronaut.petstore.dto

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-29
 */
data class PetRequest(
    val category: Category,
    val name: String,
    val photoUrls: List<String>,
    val tags: List<Tag>,
    val status: PetStatus
) {
    fun toPet(id: Long) = Pet(
        id = id,
        category = category,
        name = name,
        photoUrls = photoUrls,
        tags = tags,
        status = status
    )
}
