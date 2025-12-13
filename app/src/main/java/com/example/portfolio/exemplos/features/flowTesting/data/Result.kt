package com.example.portfolio.exemplos.features.flowTesting.data

sealed interface Result<out T> {
    data object Loading : Result<Nothing>
    data class Success<T>(val data: T) : Result<T>
    data class Failure(val throwable: Throwable? = null) : Result<Nothing>
}