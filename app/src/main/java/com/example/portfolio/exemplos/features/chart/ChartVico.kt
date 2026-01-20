package com.example.portfolio.exemplos.features.chart
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme
import com.skydoves.compose.stability.runtime.TraceRecomposition
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// 1. Modelo de Dados
@Stable
data class ChartData(
    val date: LocalDateTime,
    val value: Float
)

// 2. Mock de Dados (Exemplo)
val mockDataList = listOf(
    ChartData(LocalDateTime.of(2023, 10, 27, 8, 0), 20f),
    ChartData(LocalDateTime.of(2023, 10, 27, 9, 0), 45f),
    ChartData(LocalDateTime.of(2023, 10, 27, 10, 0), 28f),
    ChartData(LocalDateTime.of(2023, 10, 27, 11, 0), 80f),
    ChartData(LocalDateTime.of(2023, 10, 27, 12, 0), 100f),
    ChartData(LocalDateTime.of(2023, 10, 27, 13, 0), 55f),
    ChartData(LocalDateTime.of(2023, 10, 27, 14, 0), 60f),
    ChartData(LocalDateTime.of(2023, 10, 27, 8, 0), 20f),
    ChartData(LocalDateTime.of(2023, 10, 27, 9, 0), 45f),
    ChartData(LocalDateTime.of(2023, 10, 27, 10, 0), 28f),
    ChartData(LocalDateTime.of(2023, 10, 27, 11, 0), 80f),
    ChartData(LocalDateTime.of(2023, 10, 27, 12, 0), 100f),
    ChartData(LocalDateTime.of(2023, 10, 27, 13, 0), 55f),
    ChartData(LocalDateTime.of(2023, 10, 27, 14, 0), 60f),
    ChartData(LocalDateTime.of(2023, 10, 27, 8, 0), 20f),
    ChartData(LocalDateTime.of(2023, 10, 27, 9, 0), 45f),
    ChartData(LocalDateTime.of(2023, 10, 27, 10, 0), 28f),
    ChartData(LocalDateTime.of(2023, 10, 27, 11, 0), 80f),
    ChartData(LocalDateTime.of(2023, 10, 27, 12, 0), 100f),
    ChartData(LocalDateTime.of(2023, 10, 27, 13, 0), 55f),
    ChartData(LocalDateTime.of(2023, 10, 27, 14, 0), 60f),
    ChartData(LocalDateTime.of(2023, 10, 27, 8, 0), 20f),
    ChartData(LocalDateTime.of(2023, 10, 27, 9, 0), 45f),
    ChartData(LocalDateTime.of(2023, 10, 27, 10, 0), 28f),
    ChartData(LocalDateTime.of(2023, 10, 27, 11, 0), 80f),
    ChartData(LocalDateTime.of(2023, 10, 27, 12, 0), 100f),
    ChartData(LocalDateTime.of(2023, 10, 27, 13, 0), 55f),
    ChartData(LocalDateTime.of(2023, 10, 27, 14, 0), 60f)
)

@Composable
fun LineChartScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Text(
            text = "Desempenho Diário",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Chamada do componente de gráfico
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Box(modifier = Modifier.padding(16.dp)) {
                SimpleLineChart(
                    data = mockDataList,
                    lineColor = Color(0xFF6200EE)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Lista simples dos valores abaixo do gráfico
        Text(text = "Detalhes:", style = MaterialTheme.typography.titleMedium)
        mockDataList.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = item.date.format(DateTimeFormatter.ofPattern("HH:mm")))
                Text(text = "R$ ${item.value.toInt()}", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SimpleLineChart(
    data: List<ChartData>,
    modifier: Modifier = Modifier,
    lineColor: Color = MaterialTheme.colorScheme.primary
) {
    if (data.isEmpty()) return

    // Prepara os valores para escala
    val spacing = 100f // Espaço lateral
    val upperValue = (data.maxOfOrNull { it.value }?.plus(10)) ?: 100f // Máximo Y com folga
    val lowerValue = 0f // Mínimo Y (assumindo 0 para base)

    // Formatador de data simples
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    // Variáveis de cor para o gráfico
    val transparentGraphColor = remember {
        Brush.verticalGradient(
            colors = listOf(lineColor.copy(alpha = 0.5f), Color.Transparent)
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        // Área útil de desenho
        val spacePerHour = (size.width - spacing) / data.size

        // Caminho da linha principal
        val strokePath = Path().apply {
            val height = size.height
            data.indices.forEach { i ->
                val info = data[i]
                // Regra de três para achar a posição X e Y
                val ratio = info.value / upperValue

                val x1 = spacing + i * spacePerHour
                val y1 = height - (ratio * height)

                if (i == 0) {
                    moveTo(x1, y1)
                } else {
                    // lineTo cria linhas retas.
                    // Para curvas suaves seria necessário cubicTo ou quadraticBezierTo
                    lineTo(x1, y1)
                }
            }
        }

        // Caminho para o preenchimento (sombra) abaixo da linha
        val fillPath = android.graphics.Path(strokePath.asAndroidPath())
            .asComposePath()
            .apply {
                lineTo(spacing + (data.size - 1) * spacePerHour, size.height)
                lineTo(spacing, size.height)
                close()
            }

        // 1. Desenha o preenchimento gradiente
        drawPath(
            path = fillPath,
            brush = transparentGraphColor
        )

        // 2. Desenha a linha sólida
        drawPath(
            path = strokePath,
            color = lineColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )

        // 3. Desenha os pontos e textos (Eixos)
        data.forEachIndexed { i, info ->
            val height = size.height
            val ratio = info.value / upperValue

            val x1 = spacing + i * spacePerHour
            val y1 = height - (ratio * height)

            // Círculo no ponto
            drawCircle(
                color = lineColor,
                radius = 4.dp.toPx(),
                center = Offset(x1, y1)
            )

            // Círculo branco dentro para dar efeito de anel
            drawCircle(
                color = Color.White,
                radius = 2.dp.toPx(),
                center = Offset(x1, y1)
            )

            // Labels do Eixo X (Hora)
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    info.date.format(timeFormatter),
                    x1,
                    size.height + 40f, // Posição abaixo do gráfico
                    Paint().apply {
                        color = android.graphics.Color.GRAY
                        textSize = 30f
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChart() {
    PortfolioExemplosTheme {
        LineChartScreen()
    }
}