package com.example.portfolio.exemplos.features.animation

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.unit.sp


@Composable
fun AnimatedGradientBackgroundCore(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
    ) {
        AnimatedGradientBackground()
    }
}

@Composable
fun AnimatedGradientBackground() {
    // 1. Configura a transição infinita
    val infiniteTransition = rememberInfiniteTransition(label = "background_transition")

    // 2. Anima a primeira cor do gradiente (ex: de Roxo para Azul)
    val startColor by infiniteTransition.animateColor(
        initialValue = Color(0xFF6200EE), // Roxo
        targetValue = Color(0xFF03DAC5),  // Verde Água
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "startColor"
    )

    // 3. Anima a segunda cor do gradiente (ex: de Vermelho para Laranja)
    val endColor by infiniteTransition.animateColor(
        initialValue = Color(0xFFB00020), // Vermelho
        targetValue = Color(0xFFFF6F00),  // Laranja
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "endColor"
    )

    // 4. Cria o Brush (pincel) do gradiente com as cores animadas
    val gradientBrush = Brush.linearGradient(
        colors = listOf(startColor, endColor),
        // Você pode ajustar o ângulo mudando o start/end offset se quiser
    )

    // 5. Aplica ao Box
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBrush),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Background Animado",
            color = Color.White,
            fontSize = 24.sp
        )
    }
}

@Composable
fun MovingGradientBackgroundCore(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
    ) {
        MovingGradientBackground()
    }
}

@Composable
fun MovingGradientBackground() {
    val infiniteTransition = rememberInfiniteTransition(label = "move_transition")

    // Anima um valor de 0f a 1000f (simulando movimento)
    val offsetAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "offset"
    )

    // Cores fixas, mas o gradiente se move
    val brush = Brush.linearGradient(
        colors = listOf(Color.Blue, Color.Magenta, Color.Cyan),
        start = androidx.compose.ui.geometry.Offset(offsetAnimation, 0f),
        end = androidx.compose.ui.geometry.Offset(offsetAnimation + 500f, 1000f),
        tileMode = TileMode.Mirror // Espelha o gradiente para preencher
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush)
    )
}
