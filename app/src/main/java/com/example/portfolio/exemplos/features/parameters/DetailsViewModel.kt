package com.example.portfolio.exemplos.features.parameters

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel(assistedFactory = DetailsViewModel.DetailsViewModelFactory::class)
class DetailsViewModel @AssistedInject constructor(
    @Assisted private val description: String
): ViewModel() {

    @AssistedFactory
    interface DetailsViewModelFactory {
        fun create(myString: String): DetailsViewModel
    }


    private val _state = MutableStateFlow(DetailsState(description))
    val state = _state.asStateFlow()

    init {
        println("DetailsViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        println("DetailsViewModel cleared")
    }
}

data class DetailsState(
    val description: String
)