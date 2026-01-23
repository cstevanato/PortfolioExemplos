package com.example.portfolio.exemplos.features.errorCustom

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio.exemplos.features.errorCustom.error.CustomError
import com.example.portfolio.exemplos.features.errorCustom.error.CustomException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ErrorCustomViewModel @Inject constructor() : ViewModel() {

    private val errorCustomRepository = ErrorCustomRepository()

    var mainError by mutableStateOf<CustomError?>(null)
        private set


    fun getData() {
        viewModelScope.launch {
            try {
                val result = errorCustomRepository.fetchData(true)
            } catch (e: CustomException) {
                mainError = e.mapToCustomError()
            } catch (e: Exception) {
                mainError = CustomError.GeneralError(e.localizedMessage)
            } finally {
                processError()
            }
        }
    }

    private fun processError() {
        when (mainError) {
//            CustomError.AnotherSpecificError -> TODO()
//            is CustomError.GeneralError -> TODO()
            CustomError.NoNetworkConnection -> {}
            else -> Unit
        }
    }

//    fun checkInputData(a: String, b: String) {
//        if(a.isEmpty() || b.isEmpty()) {
//            mainError = CustomError.InvalidInput
//        }
//    }
}