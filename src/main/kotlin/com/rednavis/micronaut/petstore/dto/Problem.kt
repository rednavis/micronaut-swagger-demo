package com.rednavis.micronaut.petstore.dto

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-30
 */
data class Problem(
    val status: Int,
    val details: String
)
