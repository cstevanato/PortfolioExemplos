package com.example.portfolio.exemplos.features.connectivity

import androidx.lifecycle.ViewModel
import com.example.portfolio.exemplos.core.connectivity.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ConnectivityViewModel @Inject constructor(
    connectivityObserver: ConnectivityObserver
) : ViewModel() {

    val status = connectivityObserver.observe()
}