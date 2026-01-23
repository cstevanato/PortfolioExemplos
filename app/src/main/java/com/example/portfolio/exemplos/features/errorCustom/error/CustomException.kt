package com.example.portfolio.exemplos.features.errorCustom.error

sealed class CustomException(
    message: String? = null
) : Exception(message) {
    object NoNetworkConnectionException : CustomException()
    object AnotherSpecificException : CustomException()

    fun mapToCustomError(): CustomError {
        return when (this) {
            is NoNetworkConnectionException -> {
                CustomError.NoNetworkConnection
            }

            is AnotherSpecificException -> {
                CustomError.AnotherSpecificError
            }

            else -> {
                CustomError.GeneralError(this.message)
            }
        }
    }
}