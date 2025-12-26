package com.example.portfolio.exemplos.features.loading


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme

@Composable
fun LoadingConcatScreen(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier, containerColor = Color.Black) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Column( horizontalAlignment = Alignment.CenterHorizontally) {
                TripleOrbitLoadingAnimation(
                    modifier = Modifier.size(100.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))

                PulseAnimation(
                    color = Color.Red
                )
                Spacer(modifier = Modifier.height(16.dp))

                BlurredAnimateText(
                    text = "Hello world..."
                )

            }
        }
    }
}

@Preview
@Composable
private fun LoadingConcatScreenPreview() {
    PortfolioExemplosTheme {
        LoadingConcatScreen(
            modifier = Modifier.fillMaxSize()
        )
    }
}