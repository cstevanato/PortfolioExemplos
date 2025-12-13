package com.example.portfolio.exemplos.features.errorHandling.data

import com.example.portfolio.exemplos.features.errorHandling.domain.DataError
import com.example.portfolio.exemplos.features.errorHandling.domain.Result
import io.ktor.client.HttpClient
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient
) : AuthRepository {
    override suspend fun register(password: String): Result<User, DataError.Network> {
        return try {
//            val user = User("Jhon Doe", "william.henry.harrison@example-pet-store.com", "token")
//            Result.Success(user)
            Result.Error(DataError.Network.REQUEST_TIMEOUT)
        } catch (e: ClientRequestException) {
            when (e.response.status.value) {
                408 -> return Result.Error(DataError.Network.REQUEST_TIMEOUT)
                401 -> return Result.Error(DataError.Network.UNAUTHORIZED)
                else -> Result.Error(DataError.Network.UNKNOWN)
            }

        } catch (e: ServerResponseException) {
            Result.Error(DataError.Network.SERVER_ERROR)
        } catch (e: Exception) {
            Result.Error(DataError.Network.UNKNOWN)
        }
    }
}
