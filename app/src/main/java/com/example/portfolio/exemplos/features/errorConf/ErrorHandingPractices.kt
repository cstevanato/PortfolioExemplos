package com.example.portfolio.exemplos.features.errorConf

import java.util.Locale
import java.util.Locale.getDefault

data class UserErrorHandling(val name: String, val email: String)

class InvalidUserInputsException(message: String) : RuntimeException(message)

/**
 * @throws InvalidUserInputsException
 * @throws IllegalArgumentException
 * @throws IllegalStateException
 *
 */
fun validateUser(input: String) {
    if (input.isBlank()) {
        throw InvalidUserInputsException("Input cannot be blank")
    }

    val name: String? = getUserName()
    println(name?.uppercase(getDefault()) ?: "Unknown")
}

fun getUserName(): String? {
    return null
}



class ErrorHandingPractices
{
    // throws IllegalArgumentException
    fun setAge(age: Int) {
        require(age > 0) { "Age must be a positive number" }
    }

    fun getUserInfo(user: UserErrorHandling?) {
        // IllegalStateException
        checkNotNull(user) { "User cannot be null" }

        // IllegalArgumentException
        requireNotNull(user.email) { "Email cannot be null" }
    }
}