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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme
import kotlin.math.roundToInt

@Composable
fun AnimatedCircularProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    durationMillis: Int = 2000,
    trackColor: Color = Color.LightGray,
    progressColor: Color = Color(0xFF3F51B5),
    strokeWidth: Dp = 12.dp,
    startAngle: Float = -180f,
    showLabel: Boolean = true,
    onAnimationEnd: () -> Unit = {}
) {
    val clampedTarget = progress.coerceIn(0f, 1f)
    // Inicializa com 0f. Se quiser que comece já no progresso ao renderizar pela primeira vez,
    // mude para initialValue = clampedTarget (mas perderá a animação inicial de entrada)
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
        // Adiciona semântica para leitores de tela
        modifier = modifier
            .semantics {
                progressBarRangeInfo = ProgressBarRangeInfo(
                    current = animated.value,
                    range = 0f..1f
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokePx = strokeWidth.toPx()

            // CORREÇÃO: Subtrair o strokePx para evitar corte nas bordas
            // O diâmetro deve ser o menor lado MENOS a espessura da linha
            val diameter = size.minDimension - strokePx

            val topLeft = Offset(
                (size.width - diameter) / 2f,
                (size.height - diameter) / 2f
            )
            val arcSize = Size(diameter, diameter)

            // Trilho (Fundo)
            drawArc(
                color = trackColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )

            // Progresso (Frente)
            drawArc(
                color = progressColor,
                startAngle = startAngle,
                // Evita desenhar nada se for 0, para não ficar um ponto estranho se cap=Round
                sweepAngle = (animated.value * 360f).coerceAtLeast(0.1f),
                useCenter = false,
                topLeft = topLeft,
                size = arcSize,
                style = Stroke(width = strokePx, cap = StrokeCap.Round)
            )
        }

        if (showLabel) {
            Text(
                text = "${(animated.value * 100).roundToInt()}%",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface // Garante contraste
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
    // Estado do progresso alvo (ex: 0.8f, 0.5f, etc)
    var targetProgress by remember { mutableFloatStateOf(0f) }

    // Estado da duração
    var durationMillis by remember { mutableIntStateOf(1500) }

    // Chave para forçar a recriação do componente (Reiniciar)
    var restartKey by remember { mutableIntStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center) {

                // O segredo está aqui: key(restartKey)
                // Sempre que 'restartKey' mudar, o Compose descarta o AnimatedCircularProgress
                // antigo e cria um novo. O novo nasce com valor 0 e anima até o targetProgress.
                key(restartKey) {
                    AnimatedCircularProgress(
                        progress = targetProgress,
                        durationMillis = durationMillis,
                        modifier = Modifier.size(140.dp),
                        trackColor = Color(0xFFE0E0E0),
                        progressColor = Color(0xFF26A69A),
                        strokeWidth = 10.dp,
                        onAnimationEnd = {
                            println("Fim da animação")
                        }
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            // --- CONTROLES ---

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                // Botão: Definir valor e animar (comportamento padrão)
                Button(onClick = { targetProgress = 0.8f }) {
                    Text("Ir para 80%")
                }

                // Botão: REINICIAR (A mágica acontece aqui)
                Button(
                    onClick = {
                        // 1. Garante que o alvo é o desejado (caso esteja em 0)
                        if (targetProgress == 0f) targetProgress = 0.8f

                        // 2. Incrementa a chave para forçar a recriação do componente
                        restartKey++
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text("Reiniciar")
                }
            }

            Spacer(Modifier.height(12.dp))

            Button(onClick = { targetProgress = 0f }) {
                Text("Zerar (Animado)")
            }

            Spacer(Modifier.height(24.dp))

            Text("Duração: ${durationMillis}ms")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = { durationMillis = 1000 }) { Text("1s") }
                OutlinedButton(onClick = { durationMillis = 2000 }) { Text("2s") }
            }
        }
    }
}