package com.rednavis.micronaut.petstore.dto

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-20
 */
data class Pet(
    val id: Long?,
    val category: Category?,
    val name: String?,
    val photoUrls: List<String>?,
    val tags: List<Tag>?,
    val status: PetStatus
)
