package com.example.portfolio.exemplos.features.flowTesting.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun AddressScreen(
    viewModel: AddressViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            var cep by remember { mutableStateOf("") }

            when (state) {
                is AddressState.Idle -> {
                    Text(text = "Idle")
                }

                is AddressState.Loading -> {
                    Text(text = "Loading")
                }

                is AddressState.Success -> {
                    Text(text = "Success ${(state as AddressState.Success).data}")
                }

                is AddressState.Failure -> {
                    Text(text = "Failure ${(state as AddressState.Failure).throwable}")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = cep,
                onValueChange = {
                    cep = it
                },
                label = { Text("Cep") },
                placeholder = { Text("Digite CEP para pesquisa") },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {viewModel.findAddressSuspend(cep)}) {
                Text(text = "Find address suspend")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {viewModel.findAddressFlow(cep)}) {
                Text(text = "Find address flow")
            }

        }
    }
}