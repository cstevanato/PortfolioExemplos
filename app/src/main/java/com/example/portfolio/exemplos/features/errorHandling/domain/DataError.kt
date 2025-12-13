package com.example.portfolio.exemplos.features.errorHandling.domain

sealed interface DataError : Error {
    enum class Network : DataError {
        UNAUTHORIZED,
        REQUEST_TIMEOUT,
        SERVER_ERROR,
        TOO_MANY_REQUESTS,
        UNKNOWN,
        NO_INTERNET_CONNECTION,
        PAYLOAD_TOO_LARGE,
        SERIALIZATION,
        DESERIALIZATION,
    }
    enum class Local: DataError {
        USER_NOT_FOUND,
        DISK_FULL,
    }
}

