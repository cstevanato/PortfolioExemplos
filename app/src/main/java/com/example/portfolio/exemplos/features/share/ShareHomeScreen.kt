package com.example.portfolio.exemplos.features.share

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ShareHomeScreen(
    viewModel: ShareHomeViewModel = hiltViewModel(),
    sharedViewModel: ShareViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onNavigateToMainHome: () -> Unit,
    onNavigateToShareRegister: () -> Unit
) {
    val localCounter by viewModel.counter.collectAsStateWithLifecycle()
    val sharedCounter by sharedViewModel.counter.collectAsStateWithLifecycle()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = modifier.padding(paddingValues).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                onClick = sharedViewModel::bumpCounter
            ) {
                Text("Shared counter: $sharedCounter")
            }

            Button(
                onClick = viewModel::bumpCounter
            ) {
                Text("local counter: $localCounter")
            }

            Button(
                onClick = onNavigateToMainHome
            ) {
                Text("Navigation to Main Home")
            }
            Button(
                onClick = onNavigateToShareRegister
            ) {
                Text("Navigation to Share Register")
            }
        }
    }
}