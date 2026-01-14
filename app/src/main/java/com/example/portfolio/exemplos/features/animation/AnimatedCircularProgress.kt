package com.example.portfolio.exemplos.features.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme
import kotlin.math.roundToInt

@Composable
fun AnimatedCircularProgress(
    progress: Float,                 // alvo entre 0f..1f
    durationMillis: Int = 2000,      // tempo da animação
    modifier: Modifier = Modifier,
    trackColor: Color = Color.LightGray,
    progressColor: Color = Color(0xFF3F51B5),
    strokeWidth: Dp = 12.dp,
    startAngle: Float = -90f,        // começa no topo
    showLabel: Boolean = true
) {
    val clampedTarget = progress.coerceIn(0f, 1f)
    val animated = remember { Animatable(0f) }

    // Anima até o novo alvo sempre que 'progress' mudar
    LaunchedEffect(clampedTarget, durationMillis) {
        animated.animateTo(
            targetValue = clampedTarget,
            animationSpec = tween(
                durationMillis = durationMillis,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val strokePx = strokeWidth.toPx()
            val diameter = size.minDimension
            val topLeft = Offset(
                (size.width - diameter) / 2f,
                (size.height - diameter) / 2f
            )
            val arcSize = Size(diameter, diameter)

            // Trilho (fundo)
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )

            // Progresso
            drawArc(
                color = progressColor,
                startAngle = startAngle,
                sweepAngle = animated.value * 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }

        if (showLabel) {
            Text(
                text = "${(animated.value * 100).roundToInt()}%",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedCircularProgressPreview() {
    PortfolioExemplosTheme {
        AnimatedCircularProgress(
            progress = 0.8f,
            durationMillis = 1500,
            modifier = Modifier.size(140.dp),
            trackColor = Color(0xFFE0E0E0),
            progressColor = Color(0xFF26A69A),
            strokeWidth = 10.dp
        )
    }
}

@Composable
fun AnimatedCircularProgressScreen() {
    Surface(modifier = Modifier.fillMaxSize() ) {
        Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center) {
            AnimatedCircularProgress(
                progress = 0.8f,
                durationMillis = 1500,
                modifier = Modifier.size(140.dp),
                trackColor = Color(0xFFE0E0E0),
                progressColor = Color(0xFF26A69A),
                strokeWidth = 10.dp
            )
        }
    }
}

// Exemplo de uso:
// AnimatedCircularProgress(
//     progress = 0.8f,
//     durationMillis = 1500,
//     modifier = Modifier.size(140.dp),
//     trackColor = Color(0xFFE0E0E0),
//     progressColor = Color(0xFF26A69A),
//     strokeWidth = 10.dp
// )