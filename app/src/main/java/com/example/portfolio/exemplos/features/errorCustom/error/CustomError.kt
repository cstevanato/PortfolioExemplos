package com.example.portfolio.exemplos.features.errorCustom.error

import androidx.compose.runtime.Immutable

@Immutable
sealed class CustomError {
    object NoNetworkConnection: CustomError()
    object AnotherSpecificError: CustomError()
    data class GeneralError(val message: String?) : CustomError()
}