package com.example.portfolio.exemplos.features.errorCustom

import com.example.portfolio.exemplos.features.errorCustom.error.CustomException

class ErrorCustomRepository {
    suspend fun fetchData(throwException: Boolean): String {
        if (throwException) {
            throw CustomException.NoNetworkConnectionException
        }
        return "Test"
    }
}