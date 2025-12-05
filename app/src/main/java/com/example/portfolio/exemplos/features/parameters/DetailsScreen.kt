package com.example.portfolio.exemplos.features.parameters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme

/**
 *  Example of passing parameters between screens using navigation 3.
 *  ViewModel displays the lifecycle calls when: Created and Cleared
 */

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    DetailsContent(state.description)
}

@Composable
private fun DetailsContent(description: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Details: $description")
    }
}

@Composable
@Preview
fun DetailsScreenPreview() {
    PortfolioExemplosTheme {
        DetailsContent("123")
    }
}


