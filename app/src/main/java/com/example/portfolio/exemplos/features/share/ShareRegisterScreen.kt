package com.example.portfolio.exemplos.features.share

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ShareRegisterScreen(
    viewModel: ShareRegisterViewModel = hiltViewModel(),
    sharedViewModel: ShareViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
) {
    val localCounter by viewModel.counter.collectAsStateWithLifecycle()
    val sharedCounter by sharedViewModel.counter.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize(),
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
    }

}