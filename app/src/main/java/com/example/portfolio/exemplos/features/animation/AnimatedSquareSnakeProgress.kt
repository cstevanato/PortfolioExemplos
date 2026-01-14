package com.example.portfolio.exemplos.features.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun AnimatedSquareSnakeProgress(
    progress: Float,
    modifier: Modifier = Modifier,
    durationMillis: Int = 3000, // Mais lento para ver a cobra andar
    trackColor: Color = Color.LightGray.copy(alpha = 0.3f),
    progressColor: Color = Color(0xFF4CAF50), // Verde Snake
    strokeWidth: Dp = 16.dp, // Grosso para parecer blocos
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
                easing = LinearEasing // Linear fica mais parecido com jogo antigo
            )
        )
        if (result.endReason == AnimationEndReason.Finished) {
            latestOnEnd()
        }
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val strokePx = strokeWidth.toPx()

            // 1. Gerar o caminho completo da espiral (do topo-esq ao centro)
            val fullPath = generateSquareSpiralPath(
                size = size.minDimension,
                strokeWidth = strokePx
            )

            // 2. Desenhar o "Trilho" (A espiral completa apagada no fundo)
            drawPath(
                path = fullPath,
                color = trackColor,
                style = Stroke(
                    width = strokePx,
                    cap = StrokeCap.Square, // Pontas quadradas
                    join = StrokeJoin.Miter // Quinas retas
                )
            )

            // 3. Recortar o caminho baseado no progresso
            // PathMeasure mede o comprimento total do caminho e extrai um segmento
            val pathMeasure = PathMeasure()
            pathMeasure.setPath(fullPath, false)
            val totalLength = pathMeasure.length

            val segmentPath = Path()
            // Extrai do 0 até (comprimento * progresso)
            pathMeasure.getSegment(
                startDistance = 0f,
                stopDistance = totalLength * animated.value,
                destination = segmentPath,
                startWithMoveTo = true
            )

            // 4. Desenhar a "Cobra" (o segmento extraído)
            drawPath(
                path = segmentPath,
                color = progressColor,
                style = Stroke(
                    width = strokePx,
                    cap = StrokeCap.Square, // Importante para visual "pixel/bloco"
                    join = StrokeJoin.Miter
                )
            )
        }

        if (showLabel) {
            Text(
                text = "${(animated.value * 100).roundToInt()}%",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

/**
 * Lógica matemática para criar uma espiral quadrada contínua.
 * Começa no canto superior esquerdo e enrola para dentro.
 */
fun generateSquareSpiralPath(size: Float, strokeWidth: Float): Path {
    val path = Path()

    // Margem inicial (metade do stroke para não cortar a borda)
    var currentX = strokeWidth / 2
    var currentY = strokeWidth / 2

    // Tamanho atual do lado a ser desenhado
    var currentSize = size - strokeWidth

    // Inicia o caminho
    path.moveTo(currentX, currentY)

    // Direção: 0=Dir, 1=Baixo, 2=Esq, 3=Cima
    var direction = 0

    // Enquanto houver espaço para desenhar (tamanho > espessura)
    // O loop cria linha por linha
    while (currentSize > 0) {
        when (direction) {
            0 -> { // Direita
                currentX += currentSize
                path.lineTo(currentX, currentY)
            }
            1 -> { // Baixo
                currentY += currentSize
                path.lineTo(currentX, currentY)
            }
            2 -> { // Esquerda
                currentX -= currentSize
                path.lineTo(currentX, currentY)
            }
            3 -> { // Cima
                currentY -= currentSize
                path.lineTo(currentX, currentY)
            }
        }

        // A cada duas pernas (ex: Dir+Baixo), o tamanho do próximo quadrado diminui
        // pelo dobro da espessura da linha (para caber dentro do anterior)
        if (direction % 2 == 1) { // Após Baixo e após Cima
            currentSize -= strokeWidth * 2

            // Ajuste fino: Se o espaço ficou menor que o traço, paramos no centro
            if (currentSize < -strokeWidth) break
        }

        // Próxima direção
        direction = (direction + 1) % 4
    }

    return path
}

// --- TELA DE TESTE ---

@Composable
fun SnakeSquareScreen() {
    var targetProgress by remember { mutableFloatStateOf(0f) }
    var restartKey by remember { mutableIntStateOf(0) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier.size(300.dp),
                contentAlignment = Alignment.Center
            ) {
                // Key para reiniciar animação do zero
                key(restartKey) {
                    AnimatedSquareSnakeProgress(
                        progress = targetProgress,
                        modifier = Modifier.size(250.dp), // Quadrado
                        durationMillis = 4000, // Demora mais pois o caminho é longo
                        strokeWidth = 20.dp,   // Traço grosso
                        trackColor = Color(0xFFE0E0E0),
                        progressColor = Color(0xFF388E3C)
                    )
                }
            }

            Spacer(Modifier.height(32.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { targetProgress = 1f }) {
                    Text("Preencher (100%)")
                }

                Button(
                    onClick = {
                        if (targetProgress == 0f) targetProgress = 1f
                        restartKey++
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
                ) {
                    Text("Reiniciar")
                }
            }

            Spacer(Modifier.height(16.dp))

            Button(onClick = { targetProgress = 0f }) {
                Text("Esvaziar")
            }
        }
    }
}