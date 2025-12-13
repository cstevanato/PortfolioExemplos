package com.example.portfolio.exemplos.features.flowTesting.data

import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    suspend fun findAddress(cep: String): Result<AddressDto>
    fun findAddressFlow(cep: String): Flow<Result<AddressDto>>
}