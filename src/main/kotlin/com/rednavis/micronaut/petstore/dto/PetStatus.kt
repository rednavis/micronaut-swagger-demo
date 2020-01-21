package com.rednavis.micronaut.petstore.dto

import com.fasterxml.jackson.annotation.JsonProperty

/**
 * @author Dmitry Tsydzik
 * @since 2020-01-20
 */
enum class PetStatus {
    @JsonProperty("available")
    AVAILABLE,
    @JsonProperty("pending")
    PENDING,
    @JsonProperty("sold")
    SOLD
}
