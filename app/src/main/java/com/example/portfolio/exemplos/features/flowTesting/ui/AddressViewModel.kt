package com.example.portfolio.exemplos.features.flowTesting.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio.exemplos.features.flowTesting.data.AddressDto
import com.example.portfolio.exemplos.features.flowTesting.data.AddressRepository
import com.example.portfolio.exemplos.features.flowTesting.data.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModel @Inject constructor(
    private val addressRepository: AddressRepository
) : ViewModel() {

    private val _state = MutableStateFlow<AddressState>(AddressState.Idle)
    val state = _state.asStateFlow()

    fun findAddressSuspend(cep: String) {
        viewModelScope.launch {
            _state.update { AddressState.Loading }

            when (val result = addressRepository.findAddress(cep)) {
                is Result.Success -> _state.update { AddressState.Success(result.data) }
                is Result.Failure -> _state.update { AddressState.Failure(result.throwable) }
                else -> Unit
            }
        }
    }

    fun findAddressFlow(cep: String) {
        addressRepository
            .findAddressFlow(cep)
            .onEach { result ->
                when (result) {
                    is Result.Success -> _state.update { AddressState.Success(result.data) }
                    is Result.Failure -> _state.update { AddressState.Failure(result.throwable) }
                    is Result.Loading -> _state.update { AddressState.Loading }
                }
            }
            .launchIn(viewModelScope)
    }

}

sealed interface AddressState {
    data object Idle : AddressState
    data object Loading : AddressState
    data class Success(val data: AddressDto) : AddressState
    data class Failure(val throwable: Throwable? = null) : AddressState
}