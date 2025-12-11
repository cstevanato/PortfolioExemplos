package com.example.portfolio.exemplos.features.connectivity

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.portfolio.exemplos.core.connectivity.ConnectivityObserver

@Composable
fun ConnectivityScreen(viewModel: ConnectivityViewModel = hiltViewModel()) {
    val status =
        viewModel.status.collectAsState(initial = ConnectivityObserver.Status.Unavailable)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Status: ${status.value}")
    }
}