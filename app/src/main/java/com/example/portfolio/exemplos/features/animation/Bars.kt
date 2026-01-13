package com.example.portfolio.exemplos.features.animation

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme
import kotlin.math.min

@Composable
fun ExampleScreen() {
    // Exemplo: total 1000, gasto 320
    Surface(modifier = Modifier.fillMaxSize())
    {
        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)) {
            SpendingBar(
                total = 1000f,
                spent = 920f, // >= 90% ficará vermelho
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun SpendingBar(
    total: Float,
    spent: Float,
    modifier: Modifier = Modifier,
    barHeight: Dp = 18.dp,
    backgroundColor: Color = Color(0xFFEAEAEA),
    normalColor: Color = Color(0xFF4CAF50), // verde
    dangerColor: Color = Color(0xFFFF5252), // vermelho
    cornerRadius: Dp = 10.dp,
    animationMillis: Int = 1000,
    dangerThreshold: Float = 0.90f // 90%
) {
    val targetFraction = remember(total, spent) {
        if (total <= 0f) 0f else min(spent / total, 1f)
    }

    var animatedFraction by remember { mutableStateOf(0f) }
    LaunchedEffect(targetFraction) {
        androidx.compose.animation.core.animate(
            initialValue = animatedFraction,
            targetValue = targetFraction,
            animationSpec = tween(durationMillis = animationMillis, easing = FastOutSlowInEasing)
        ) { value, _ ->
            animatedFraction = value
        }
    }

    // Cor muda para vermelho quando >= 90%
    val progressColor by animateColorAsState(
        targetValue = if (animatedFraction >= dangerThreshold) dangerColor else normalColor,
        animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing)
    )

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Gasto", style = MaterialTheme.typography.bodyMedium)
            val percent = (animatedFraction * 100).toInt()
            Text(text = "${formatMoney(spent)} / ${formatMoney(total)} • $percent%")
        }

        Spacer(Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(barHeight)
                .background(backgroundColor, shape = RoundedCornerShape(cornerRadius))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(animatedFraction)
                    .background(progressColor, shape = RoundedCornerShape(cornerRadius))
            )
        }
    }
}

// Formatação simples de moeda (personalize conforme necessidade)
private fun formatMoney(value: Float): String {
    return "R$ " + String.format("%.2f", value)
}


@Composable
fun SpendingLinearIndicator(
    total: Float,
    spent: Float,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF4CAF50),
    trackColor: Color = Color(0xFFEAEAEA),
    animationMillis: Int = 1000
) {
    val target = if (total <= 0f) 0f else min(spent / total, 1f)
    var progress by remember { mutableStateOf(0f) }

    LaunchedEffect(target) {
        androidx.compose.animation.core.animate(
            initialValue = progress,
            targetValue = target,
            animationSpec = tween(durationMillis = animationMillis, easing = FastOutSlowInEasing)
        ) { value, _ ->
            progress = value
        }
    }

    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Gasto", fontWeight = FontWeight.SemiBold)
            Text("${formatMoney(spent)} / ${formatMoney(total)} • ${(progress * 100).toInt()}%")
        }
        Spacer(Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp),
            color = color,
            trackColor = trackColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BarPreview1() {
    PortfolioExemplosTheme {
        SpendingBar(
            total = 1000f,
            spent = 320f, // >= 90% ficará vermelho
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BarPreview2() {
    PortfolioExemplosTheme {
        SpendingBar(
            total = 1000f,
            spent = 920f, // >= 90% ficará vermelho
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}