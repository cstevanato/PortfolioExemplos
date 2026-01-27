package com.example.portfolio.exemplos.features.errorConf

import kotlinx.serialization.json.Json

data class User(val name: String)

class NetworkException : Exception()
class ParsingException : Exception()
class ValidationException(val reason: String) : Exception(reason)


fun fetchUserResult(): Result<Json> {
    return Result.failure(NetworkException())
}

fun Json.parseUserResult(): Result<User> {
    return Result.failure(ParsingException())
}



fun getUserResult(name: String): Result<User> {
    if (name.length < 4) {
        return Result.failure(ValidationException("Name must be at least 4 characters long"))
    }
    val user = fetchUserResult().getOrElse { return Result.failure(it) }
    val parsedUser = user.parseUserResult().getOrElse { return Result.failure(it) }
    return Result.success(parsedUser)
}


fun usageResult() {
    getUserResult("test")
        .onSuccess { println("Success: ${it.name}") }
        .onFailure { error ->
            when (error) {
                is NetworkException -> println("Network error")
                is ParsingException -> println("Parsing error")
                is ValidationException -> println("Validation error: ${error.reason}")
                else -> println("Unknown error")
            }
        }
}
