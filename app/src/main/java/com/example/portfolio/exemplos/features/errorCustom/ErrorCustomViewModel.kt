package com.example.portfolio.exemplos.features.errorCustom

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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

    private val _mainError = mutableStateOf<CustomError?>(null)
    val mainError: State<CustomError?> = _mainError


    fun getData() {
        viewModelScope.launch {
            try {
                val result = errorCustomRepository.fetchData(true)
            } catch (e: CustomException) {
                _mainError.value = e.mapToCustomError()
            } catch (e: Exception) {
                _mainError.value = CustomError.GeneralError(e.localizedMessage)
            } finally {
                processError()
            }
        }
    }

    private fun processError() {
        when (mainError.value) {
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