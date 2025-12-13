package com.example.portfolio.exemplos.features.errorHandling.ui

import com.example.portfolio.exemplos.R
import com.example.portfolio.exemplos.features.errorHandling.domain.DataError
import com.example.portfolio.exemplos.features.errorHandling.domain.Result

fun DataError.asUiText(): UiText {
    return when(this) {
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(R.string.request_timeout)
        DataError.Network.UNAUTHORIZED -> UiText.StringResource(R.string.unauthorized)
        DataError.Network.SERVER_ERROR -> UiText.StringResource(R.string.server_error)
        else -> UiText.StringResource(R.string.unknown_error)
    } as UiText
}

fun Result.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}