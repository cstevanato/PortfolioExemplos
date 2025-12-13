package com.example.portfolio.exemplos.features.errorHandling.data

import com.example.portfolio.exemplos.features.errorHandling.domain.DataError
import com.example.portfolio.exemplos.features.errorHandling.domain.Result

interface AuthRepository {
    suspend fun register(password: String): Result<User, DataError.Network>
}

data class User(
    val fullName: String,
    val email: String,
    val token: String,
)

