package com.example.portfolio.exemplos.features.buttons

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonEffectScreen() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AnimatedButton(
                onClick = {
                    // Ação do clique
                    println("Botão clicado!")
                },
                text = "Clique Aqui"
            )

            Spacer(modifier = Modifier.height(16.dp))

            AdvancedAnimatedButton(
                onClick = {
                    // Outra ação
                },
                text = "Botão Avançado"
            )
            Spacer(modifier = Modifier.height(16.dp))
            RippleAnimatedButton(
                onClick = {
                    // Outra ação
                },
                text = "Botão Ripple"
            )

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { println("Claudio Click") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
                    .pulse()
            ) {
                Text(text = "Pulse")
            }

            Spacer(modifier = Modifier.height(16.dp))

            val  color1 = Color(0xFFE91E63)
            val  color2 = Color(0xFFFFC107)

            color2
            GradientButton(
                text = "Gradient Button",
                textColor = Color.White,
                shape = RoundedCornerShape(2.dp),
                gradient = Brush.horizontalGradient(
                    colors = listOf(
                        color1,
                        color2
                    )
                ),
                onClick = { /* Handle click */ }
            )
        }
    }
}

fun Modifier.pulse() = composed {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.7f else 1f,
        animationSpec = tween(
            durationMillis = 100,
            easing = FastOutSlowInEasing
        ),
        label = "scale"
    )

    this
        .scale(scale)
        .pointerInput(isPressed) {
            awaitPointerEventScope {
                isPressed = if (isPressed) {
                    waitForUpOrCancellation()
                    false
                } else {
                    awaitFirstDown(false)
                    true
                }
            }
        }
//        .clickable(
//            interactionSource = remember { MutableInteractionSource() },
//            indication = null
//        ) {}
}

@Composable
fun AnimatedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Clique Aqui"
) {
    var isPressed by remember { mutableStateOf(false) }

    // Animação de escala quando pressionado
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(
            durationMillis = 100,
            easing = FastOutSlowInEasing
        ),
        label = "scale"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    },
                    onTap = {
                        onClick()
                    }
                )
            }
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 32.dp, vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AdvancedAnimatedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Botão Animado"
) {
    var isPressed by remember { mutableStateOf(false) }

    // Animação de escala
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(
            durationMillis = 150,
            easing = FastOutSlowInEasing
        ),
        label = "scale"
    )

    // Animação de alpha/transparência
    val alpha by animateFloatAsState(
        targetValue = if (isPressed) 0.7f else 1f,
        animationSpec = tween(
            durationMillis = 150
        ),
        label = "alpha"
    )

    // Animação de rotação (opcional)
    val rotation by animateFloatAsState(
        targetValue = if (isPressed) 2f else 0f,
        animationSpec = tween(
            durationMillis = 150
        ),
        label = "rotation"
    )

    Box(
        modifier = modifier
            .scale(scale)
            .rotate(rotation)
            .alpha(alpha)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        val released = tryAwaitRelease()
                        if (released) {
                            isPressed = false
                        }
                    },
                    onTap = {
                        onClick()
                    }
                )
            }
            .shadow(
                elevation = if (isPressed) 2.dp else 8.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 40.dp, vertical = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RippleAnimatedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "Click Me"
) {
    var isPressed by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.5f else 1f,
        animationSpec = tween(
            durationMillis = 100,
            easing = LinearOutSlowInEasing
        ),
        label = "scale"
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .scale(scale)
            .pointerInput(isPressed) {
                awaitPointerEventScope {
                    isPressed = if (isPressed) {
                        waitForUpOrCancellation()
                        false
                    } else {
                        awaitFirstDown(false)
                        true
                    }
                }
            },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(text = text)
    }
}

@Composable
fun GradientButton(
    text: String,
    textColor: Color,
    gradient: Brush,
    shape: Shape = ButtonDefaults.shape,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        shape = shape,
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .padding(horizontal = 16.dp, vertical = 10.dp)
            ,
            contentAlignment = Alignment.Center,
        ) {
            Text(text = text, color = textColor)
        }
    }
}

@Preview
@Composable
fun GradientButtonPreview() {
    val  color1 = Color(0xFFE91E63)
    val  color2 = Color(0xFFFFC107)

    color2
    GradientButton(
        text = "Gradient Button",
        textColor = Color.White,
        shape = RoundedCornerShape(4.dp),
        gradient = Brush.horizontalGradient(
            colors = listOf(
                color1,
                color2
            )
        ),
        onClick = { /* Handle click */ }
    )
}

@Preview
@Composable
fun AnimatedButtonPreview() {
    AnimatedButton(
        onClick = {
            // Ação do clique
            println("Botão clicado!")
        },
        text = "Clique Aqui"
    )
}

@Preview
@Composable
fun AdvancedAnimatedButtonPreview() {
    AdvancedAnimatedButton(
        onClick = {
            // Outra ação
        },
        text = "Botão Avançado"
    )
}

@Preview
@Composable
fun RippleAnimatedButtonPreview() {
    RippleAnimatedButton(
        onClick = {
            // Outra ação
        },
        text = "Botão Ripple"
    )
}

@Preview
@Composable
fun ButtonEffectScreenPreview() {
    Button(
        onClick = { println("Claudio Click") },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
            .pulse()
    ) {
        Text(text = "Pulse")
    }
}