package com.example.portfolio.exemplos.parameters

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(): ViewModel() {
    init {
        println("DetailsViewModel created")
    }

    override fun onCleared() {
        super.onCleared()
        println("DetailsViewModel cleared")
    }
}