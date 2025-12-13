package com.example.portfolio.exemplos.features.errorHandling.domain

interface UserDataValidator {
    fun validatePassword(password: String): Result<Unit, PasswordError>
}