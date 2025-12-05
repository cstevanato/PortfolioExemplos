package com.example.portfolio.exemplos.parameters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme

/**
 *  Example of passing parameters between screens using navigation 3.
 *  ViewModel displays the lifecycle calls when: Created and Cleared
 */

@Composable
fun DetailsScreen(id: String, viewModel: DetailsViewModel = hiltViewModel()) {
    DetailsContent(id)
}

@Composable
private fun DetailsContent(id: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Details: $id")
    }
}

@Composable
@Preview
fun DetailsScreenPreview() {
    PortfolioExemplosTheme {
        DetailsScreen("123")
    }
}


