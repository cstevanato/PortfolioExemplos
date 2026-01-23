package com.example.portfolio.exemplos.features.errorCustom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.portfolio.exemplos.features.errorCustom.error.CustomError

@Composable
fun ErrorExampleScreen(
    modifier: Modifier = Modifier,
    viewModel: ErrorCustomViewModel = hiltViewModel()
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        val errorState : CustomError? = viewModel.mainError
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            errorState?.let {
                when (it) {
                    is CustomError.NoNetworkConnection -> {
                        Text("Check your internet connection")
                    }
                    is CustomError.AnotherSpecificError -> {
                        Text("AnotherSpecificError error")
                    }
                    is CustomError.GeneralError -> {
                        Text("GeneralError error")
                    }
                }
            }
        }
    }
}