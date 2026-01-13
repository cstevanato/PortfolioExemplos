package com.example.portfolio.exemplos.features.animation

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

// rotation
// translation
// scale
// opacity
// animatedVisibility


@Composable
fun AnimationScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    var isVisible by remember { mutableStateOf(false) }
    var counter by remember { mutableStateOf(0) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
                .padding(horizontal = 16.dp, vertical = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row() {
                Button(
                    onClick = { isVisible = !isVisible },
                ) {
                    Text("Show")
                }
                Spacer(modifier = Modifier.width(8.dp))
                Button(
                    onClick = { counter++ },
                ) {
                    Text("Counter")
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row() {
                AnimatedVisibility(
                    isVisible,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    Row() {
                        Box(
                            modifier = Modifier
                                .animateEnterExit(
                                    scaleIn() + expandVertically(),
                                    scaleOut() + shrinkVertically()
                                )
                                .size(100.dp)
                                .background(color = Color.Red)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .animateEnterExit(expandVertically(), shrinkVertically())

                                .size(100.dp)
                                .background(color = Color.Red)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                AnimatedVisibilityTween(
                    isVisible,
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(color = Color.Red)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Crossfade(targetState = isVisible, label = "", animationSpec = tween(2000)) {
                if (!it) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(color = Color.Blue)
                    )
                } else
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(color = Color.Green)
                    )
            }
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedContent(
                targetState = counter, label = "",
                transitionSpec = {
                    ContentTransform(
                        targetContentEnter = scaleIn(),
                        initialContentExit = scaleOut()
                    )
                }
            ) { count ->
                Text(text = count.toString(), fontSize = 30.sp)
            }
            Spacer(modifier = Modifier.height(8.dp))
            AnimationScale()

            Spacer(modifier = Modifier.height(8.dp))
            AnimationInfinite()

            Spacer(modifier = Modifier.height(8.dp))
            AnimetionRotation()

            Spacer(modifier = Modifier.height(8.dp))
            MoveFromAToB()
        }
    }

}

@Composable
fun MoveFromAToB() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val density = LocalDensity.current

    // Animatable de Offset (2D), ideal para translações em X e Y
    val offset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }

    // Defina os pontos A e B em dp e converta para px
    // Ponto A (0.dp, 0.dp). Ponto B (exemplo: 200.dp na horizontal e 250.dp na vertical)
    val startDpX = 0.dp
    val startDpY = 0.dp
    val endDpX = 200.dp
    val endDpY = 250.dp

    val startPx = remember(density) {
        with(density) {
            Offset(startDpX.toPx(), startDpY.toPx())
        }
    }

    val endPx = remember(density) {
        with(density) {
            Offset(endDpX.toPx(), endDpY.toPx())
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            scope.launch {
                offset.animateTo(startPx)
//                // Garante que começamos no ponto A (0,0) antes de animar
//                offset.snapTo(startPx)

                // Anima do ponto A ao ponto B
                offset.animateTo(
                    targetValue = endPx,
                    animationSpec = tween(
                        durationMillis = 1000,
                        easing = FastOutSlowInEasing
                    )
                )

                // Ao finalizar a animação, mostra o toast
                Toast.makeText(context, "Fim da animação", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("Animar do A ao B")
        }

        Box(
            modifier = Modifier
                .size(320.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            // Elemento que se move
            Box(
                modifier = Modifier
                    .offset {
                        IntOffset(
                            x = offset.value.x.roundToInt(),
                            y = offset.value.y.roundToInt()
                        )
                    }
                    .size(48.dp)
                    .background(MaterialTheme.colorScheme.primary)

            )
        }
    }
}

@Composable
private fun AnimationScale() {
    val state = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    Row() {
        Button(
            onClick = {
                scope.launch {
                    state.animateTo(
                        state.value + 1,
                        animationSpec = tween(2000, 1000)
                    )
                }
            },
        ) {
            Text("Image Plus")
        }
        Button(
            onClick = {
                scope.launch {
                    state.animateTo(
                        state.value - 1,
                        animationSpec = tween(2000, 1000)
                    )
                }
            },
        ) {
            Text("Image minus")
        }
    }
    Box(
        modifier = Modifier
            .scale(state.value)
            .size(100.dp)
            .background(color = Color.Green)
    )
}

@Composable
private fun AnimationInfinite() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val scaleAnimValue by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        ),
        label = ""
    )

    Box(
        modifier = Modifier
            .scale(scaleAnimValue)
            .size(100.dp)
            .background(color = Color.Green)
    )
}

@Composable
private fun AnimetionRotation() {
    val context = LocalContext.current
    val rotation = remember { Animatable(0f) }
    var isAnimating by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .size(100.dp)
            .graphicsLayer {
                rotationZ = rotation.value
            }
            .background(Color(0xFF3F51B5)) // cor opcional
    )
    Spacer(modifier = Modifier.height(8.dp))

    Button(
        enabled = !isAnimating,
        onClick = {
            scope.launch {
                isAnimating = true
                // Reinicia para 0 antes de animar
                rotation.snapTo(0f)
                rotation.animateTo(
                    targetValue = 360f,
                    animationSpec = tween(
                        durationMillis = 1500,
                        easing = LinearEasing
                    )
                )
                Toast.makeText(context, "Animação concluída!", Toast.LENGTH_SHORT).show()
                isAnimating = false
            }
        }
    ) {
        Text(text = if (isAnimating) "Animando..." else "Iniciar animação")
    }
}

@Composable
fun AnimatedVisibilityTween(
    isVisible: Boolean,
    content: @Composable() AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        isVisible,
        enter = fadeIn(animationSpec = tween(2000, 2000)),
        exit = fadeOut(animationSpec = tween(2000, 2000)),
        content = content
    )
}

@Composable
fun AnimatedVisibilityScala(
    isVisible: Boolean,
    content: @Composable() AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        isVisible,
        enter = fadeIn() + scaleIn() + expandVertically(),
        exit = fadeOut() + scaleOut() + shrinkVertically(),
        content = content
    )
}

@Composable
fun AnimatedVisibilityExpandVertically(
    isVisible: Boolean,
    content: @Composable() AnimatedVisibilityScope.() -> Unit,
) {
    AnimatedVisibility(
        isVisible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
        content = content
    )
}



