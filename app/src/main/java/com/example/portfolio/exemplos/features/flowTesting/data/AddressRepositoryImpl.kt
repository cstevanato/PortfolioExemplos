package com.example.portfolio.exemplos.features.flowTesting.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddressRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : AddressRepository {
    override suspend fun findAddress(cep: String): Result<AddressDto> {
        return try {
            val result = httpClient
                .get("https://viacep.com.br/ws/$cep/json/")
                .body<AddressDto>()
            Result.Success(result)
        } catch (e: Exception) {
            currentCoroutineContext().ensureActive()
            e.printStackTrace()
            Result.Failure(e)
        }
    }

    override fun findAddressFlow(cep: String): Flow<Result<AddressDto>> {
        return flow {
            emit(Result.Loading)
            val result = httpClient
                .get("https://viacep.com.br/ws/$cep/json/")
            try {
                emit(Result.Success(result.body<AddressDto>()))
            } catch (e: Exception) {
                currentCoroutineContext().ensureActive()
                e.printStackTrace()
                emit(Result.Failure(e))
            }
        }
    }

}