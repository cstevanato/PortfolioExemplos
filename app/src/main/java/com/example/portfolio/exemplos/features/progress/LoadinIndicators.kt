@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.example.portfolio.exemplos.features.progress

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.CircularWavyProgressIndicator
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicatorScreen() {

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement  = Arrangement.Center
        ) {
            CircularProgressIndicator()
            Spacer(Modifier.height(8.dp))
            LoadingIndicator()
            Spacer(Modifier.height(8.dp))
            ContainedLoadingIndicator()
            Spacer(Modifier.height(8.dp))

            val transition = rememberInfiniteTransition()
            val progress by transition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween (durationMillis = 6000)
                )
            )
            LinearProgressIndicator(progress = { progress })
            Spacer(Modifier.height(8.dp))
            CircularProgressIndicator(progress = { progress })
            Spacer(Modifier.height(8.dp))
            CircularWavyProgressIndicator(
                progress = { progress },
            )
            Spacer(Modifier.height(8.dp))
            LinearWavyProgressIndicator(
                progress = { progress },
            )
        }
    }
}