package com.rednavis.micronaut.petstore.pet

import com.rednavis.micronaut.petstore.dto.Pet
import com.rednavis.micronaut.petstore.dto.PetRequest
import com.rednavis.micronaut.petstore.dto.PetStatus
import com.rednavis.micronaut.petstore.exceptions.NotFoundException

interface PetService {

    @Throws(NotFoundException::class)
    suspend fun findById(id: Long): Pet

    suspend fun findAll(): List<Pet>

    suspend fun findAllByStatus(petStatus: PetStatus): List<Pet>

    @Throws(NotFoundException::class)
    suspend fun deleteById(id: Long)

    suspend fun create(petRequest: PetRequest): Pet

    @Throws(NotFoundException::class)
    suspend fun update(id: Long, petRequest: PetRequest): Pet
}
