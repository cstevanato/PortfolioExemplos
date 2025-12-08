package com.example.portfolio.exemplos.features.share

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ShareHomeViewModel : ViewModel() {

    private val _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    fun bumpCounter() {
        _counter.value++
    }

}