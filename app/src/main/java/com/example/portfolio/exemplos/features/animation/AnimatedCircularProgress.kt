package com.example.portfolio.exemplos.features.animation


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun AnimatedCircularProgress(
    progress: Float,                 // alvo entre 0f..1f
    durationMillis: Int = 2000,      // tempo da animação
    modifier: Modifier = Modifier,
    trackColor: Color = Color.LightGray,
    progressColor: Color = Color(0xFF3F51B5),
    strokeWidth: Dp = 12.dp,
    startAngle: Float = -90f,
    showLabel: Boolean = true,
    onAnimationEnd: () -> Unit = {}  // HOF chamado ao finalizar
) {
    val clampedTarget = progress.coerceIn(0f, 1f)
    val animated = remember { Animatable(0f) }
    val latestOnEnd by rememberUpdatedState(onAnimationEnd)

    LaunchedEffect(clampedTarget, durationMillis) {
        // Evita disparar callback se já estiver no alvo
        if (animated.value == clampedTarget) return@LaunchedEffect

        val result = animated.animateTo(
            targetValue = clampedTarget,
            animationSpec = tween(
                durationMillis = durationMillis,
                easing = FastOutSlowInEasing
            )
        )

        if (result.endReason == AnimationEndReason.Finished) {
            // Garante que chegou ao alvo antes de chamar
            if (animated.value == clampedTarget) {
                latestOnEnd()
            }
        }
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

            // Trilho
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
    var targetProgress by remember { mutableStateOf(0f) }
    var durationMillis by remember { mutableStateOf(1500) }
    val scope = rememberCoroutineScope()

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center) {
                AnimatedCircularProgress(
                    progress = targetProgress,
                    durationMillis = durationMillis,
                    modifier = Modifier.size(140.dp),
                    trackColor = Color(0xFFE0E0E0),
                    progressColor = Color(0xFF26A69A),
                    strokeWidth = 10.dp
                ) {
                    println("fim da animação")
                }
            }

            Spacer(Modifier.height(16.dp))

            Row {
                // Iniciar animação até 0.8
                Button(onClick = {
                    scope.launch {
                        // Se quiser reexecutar mesmo que já esteja em 0.8, force um reset:
                        targetProgress = 0f
                        // aguarda um frame para recompor antes de iniciar
                        delay(16)
                        targetProgress = 0.8f
                    }
                }) {
                    Text("Iniciar")
                }

                Spacer(Modifier.width(12.dp))

                // Resetar para 0
                Button(onClick = {
                    targetProgress = 0f
                }) {
                    Text("Resetar")
                }
            }

            Spacer(Modifier.height(12.dp))

            Row {
                // Exemplo: alterar duração da animação
                Button(onClick = { durationMillis = 1000 }) { Text("1s") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { durationMillis = 2000 }) { Text("2s") }
                Spacer(Modifier.width(8.dp))
                Button(onClick = { durationMillis = 3500 }) { Text("3.5s") }
            }
        }
    }
}