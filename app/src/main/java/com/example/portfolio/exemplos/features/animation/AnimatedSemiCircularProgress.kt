package com.example.portfolio.exemplos.features.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.min
import kotlin.math.roundToInt

@Composable
fun AnimatedSemiCircularProgress(
    progress: Float,                // 0f a 1f
    modifier: Modifier = Modifier,
    durationMillis: Int = 2000,
    trackColor: Color = Color.LightGray,
    progressColor: Color = Color(0xFF3F51B5),
    strokeWidth: Dp = 12.dp,
    showLabel: Boolean = true,
    onAnimationEnd: () -> Unit = {}
) {
    val clampedTarget = progress.coerceIn(0f, 1f)
    val animated = remember { Animatable(0f) }
    val latestOnEnd by rememberUpdatedState(onAnimationEnd)

    LaunchedEffect(clampedTarget, durationMillis) {
        if (animated.value == clampedTarget) return@LaunchedEffect

        val result = animated.animateTo(
            targetValue = clampedTarget,
            animationSpec = tween(
                durationMillis = durationMillis,
                easing = FastOutSlowInEasing
            )
        )
        if (result.endReason == AnimationEndReason.Finished) {
            latestOnEnd()
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.BottomCenter // Alinha o texto na base
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokePx = strokeWidth.toPx()

            // LÓGICA DE GEOMETRIA PARA SEMICÍRCULO
            // Queremos que caiba na largura e na altura disponíveis.
            // O diâmetro ideal é baseado na largura, ou 2x a altura.
            val width = size.width
            val height = size.height

            // Calcula o maior diâmetro que cabe no espaço (respeitando stroke)
            // Considera que a altura precisa ser apenas o raio (metade do diâmetro)
            val diameter = min(width, height * 2) - strokePx
            val radius = diameter / 2

            // Tamanho do retângulo imaginário onde o círculo completo seria desenhado
            val arcSize = Size(diameter, diameter)

            // Ponto superior esquerdo para centralizar o arco horizontalmente
            // e alinhar verticalmente na parte de baixo do canvas
            val topLeft = Offset(
                x = (width - diameter) / 2,
                y = height - radius - (strokePx / 2) // Empurra para o fundo
            )

            // 1. Desenha o trilho (Fundo) - 180 graus fixos
            drawArc(
                color = trackColor,
                startAngle = 180f,      // Começa na esquerda (9h)
                sweepAngle = 180f,      // Meia volta até a direita (3h)
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )

            // 2. Desenha o progresso - Varia de 0 a 180 graus
            drawArc(
                color = progressColor,
                startAngle = 180f,
                sweepAngle = (animated.value * 180f).coerceAtLeast(0.1f),
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }

        if (showLabel) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 10.dp) // Ajuste fino da posição
            ) {
                Text(
                    text = "${(animated.value * 100).roundToInt()}%",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

// --- TELA DE EXEMPLO ---

@Composable
fun SemiCircleScreen() {
    var targetProgress by remember { mutableFloatStateOf(0f) }
    var restartKey by remember { mutableIntStateOf(0) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Container 2:1 (ex: 300 de largura, 150 de altura) fica visualmente agradável
            Box(
                modifier = Modifier.size(width = 300.dp, height = 150.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Key força a recriação (Reiniciar do zero)
                key(restartKey) {
                    AnimatedSemiCircularProgress(
                        progress = targetProgress,
                        modifier = Modifier.fillMaxSize(),
                        progressColor = Color(0xFFFF5722), // Laranja
                        trackColor = Color(0xFFFFCCBC),
                        strokeWidth = 20.dp,
                        durationMillis = 1500
                    )
                }
            }

            Spacer(Modifier.height(40.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { targetProgress = 0.75f }) {
                    Text("Ir para 75%")
                }

                Button(
                    onClick = {
                        if (targetProgress == 0f) targetProgress = 0.75f
                        restartKey++ // Reinicia a animação imediatamente
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Reiniciar")
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(onClick = { targetProgress = 0f }) {
                Text("Zerar")
            }
        }
    }
}