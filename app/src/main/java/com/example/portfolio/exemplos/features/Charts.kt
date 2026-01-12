package com.example.portfolio.exemplos.features

import android.graphics.Paint

import android.graphics.PointF
import android.graphics.Typeface
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEachIndexed
import androidx.core.graphics.minus
import androidx.core.graphics.plus
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme
import kotlin.math.ceil
import kotlin.math.floor


//@Composable
//fun LineChart(
//    modifier: Modifier = Modifier,
//    xValues: List<Int>,
//    yValues: List<Int>,
//    points: List<Float>,
//    interval: Int
//) {
//    Box(modifier = modifier, contentAlignment = Alignment.Center) {
//        Canvas(
//            modifier = Modifier
//                .padding(12.dp)
//                .fillMaxSize()
//        ) {
//
//            val textPaint = Paint().apply {
//                textSize = 24f
//                color = Color.Black.toArgb()
//            }
//
//            val maxX = xValues.max()
//            val xSpacing = size.width.div(maxX)
//
//            val maxY = yValues.max()
//            val ySpacing = size.height.div(maxY)
//
//            for (index in 0..maxX step interval) {
//                drawContext.canvas.nativeCanvas.drawText(
//                    if (index == 0) "" else index.toString(),
//                    xSpacing.times(index),
//                    size.height,
//                    textPaint
//                )
//            }
//
//            for (index in 0..maxY step interval) {
//                drawContext.canvas.nativeCanvas.drawText(
//                    if (index == 0) "" else index.toString(),
//                    0f,
//                    size.height - ySpacing.times(index),
//                    textPaint
//                )
//            }
//
//            val coordinates = mutableListOf<PointF>()
//
//            points.fastForEachIndexed { index, value ->
//                val x = xSpacing.times(xValues[index])
//                val y = size.height - (ySpacing.times(value))
//
//                coordinates.add(PointF(x, y))
//
//                drawCircle(
//                    Color.Blue,
//                    radius = 10f,
//                    center = Offset(x, y)
//                )
//            }
//            val controlPoints = calculateControlPoints(coordinates)
//
//            val path = Path().apply {
//                reset()
//                moveTo(
//                    coordinates.first().x,
//                    coordinates.first().y
//                )
//                for (point in 1 until coordinates.size - 1) {
//                    val controlPoint = controlPoints[point]
//
//                    cubicTo(
//                        controlPoint.first.x,
//                        controlPoint.first.y,
//                        controlPoint.second.x,
//                        controlPoint.second.y,
//                        coordinates[point].x,
//                        coordinates[point].y
//                    )
//                }
//            }
//            drawPath(
//                path = path,
//                color = Color.Red,
//                style = Stroke(width = 6f)
//            )
//        }
//    }
//
//}
//
//private fun calculateControlPoints(points: MutableList<PointF>): List<Pair<PointF, PointF>> {
//    val controlPoints = mutableListOf<Pair<PointF, PointF>>()
//    for (i in 1 until points.size) {
//        val previous = points[i - 1]
//        val current = points[i]
//        val next = points.getOrNull(i + 1)
//
//        val c1 = PointF(
//            previous.x + (current.x - previous.x).div(3),
//            previous.y + (current.y - previous.y).div(3)
//        )
//
//        val c2 = next?.let {
//            PointF(
//                current.x - (next.x - previous.x).div(3f),
//                current.y - (next.y - previous.y).div(3)
//            )
//        } ?: run {
//            PointF(
//                previous.x - (current.x - previous.x).div(3),
//                previous.y - (current.y - previous.y).div(3)
//            )
//        }
//        controlPoints.add(Pair(c1, c2))
//
//    }
//    return controlPoints
//}
//
//
//@Preview
//@Composable
//fun LineChartPreview(showBackground: Boolean = true) {
//    PortfolioExemplosTheme {
//        LineChart(
//            modifier = Modifier
//                .height(300.dp)
//                .fillMaxWidth()
//                .background(color = Color.White),
//            xValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12).map { it.times(10) },
//            yValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12).map { it.times(10) },
//            points = listOf(
//                0f,
//                5.4f,
//                2f,
//                6f,
//                9f,
//                4f,
//                2f,
//                4f,
//                8f,
//                1f,
//                11f,
//                3f
//            ).map { it.times(10f) },
//            interval = 10
//        )
//    }
//}

fun smoothBezierPath(points: List<Offset>, smoothness: Float = 0.2f): Path {
    val path = Path()
    if (points.isEmpty()) return path
    if (points.size == 1) {
        path.moveTo(points.first().x, points.first().y)
        return path
    }
    path.moveTo(points.first().x, points.first().y)
    for (i in 0 until points.size - 1) {
        val p0 = if (i - 1 >= 0) points[i - 1] else points[i]
        val p1 = points[i]
        val p2 = points[i + 1]
        val p3 = if (i + 2 < points.size) points[i + 2] else points[i + 1]

        val c1 = Offset(
            x = p1.x + (p2.x - p0.x) * smoothness,
            y = p1.y + (p2.y - p0.y) * smoothness
        )
        val c2 = Offset(
            x = p2.x - (p3.x - p1.x) * smoothness,
            y = p2.y - (p3.y - p1.y) * smoothness
        )
        path.cubicTo(c1.x, c1.y, c2.x, c2.y, p2.x, p2.y)
    }
    return path
}

/**
 * Gera ticks "bonitos" entre min e max, alinhados a um step.
 */
fun generateTicks(minV: Float, maxV: Float, step: Float): List<Float> {
    if (step <= 0f) return emptyList()
    val start = floor(minV / step) * step
    val end = ceil(maxV / step) * step
    val ticks = mutableListOf<Float>()
    var t = start
    // Proteção contra loops infinitos por precisão
    val maxIterations = 500
    var count = 0
    while (t <= end + 1e-4 && count < maxIterations) {
        ticks.add(t)
        t += step
        count++
    }
    return ticks
}

@Composable
fun SmoothLineChart(
    modifier: Modifier = Modifier,
    values: List<Float>,
    smoothness: Float = 0.2f,
    lineColor: Color = Color(0xFF3F8CFF),
    fillColor: Color = Color(0x333F8CFF),
    gridColor: Color = Color(0x22000000),
    axisColor: Color = Color(0xFF444444),
    labelColor: Color = Color(0xFF222222),
    showGrid: Boolean = true,
    // Labels e intervalos
    xLabels: List<String>? = null,       // Se null, usa índices como labels
    xTickStep: Int = 10,                  // Intervalo de ticks no X (em número de pontos)
    yTickStep: Float = 10f,               // Intervalo de ticks no Y (em unidades do valor)
    yLabelFormatter: (Float) -> String = { v -> if (v % 1f == 0f) v.toInt().toString() else "%.1f".format(v) },
    xLabelFormatter: (Int) -> String = { idx ->
        xLabels?.getOrNull(idx) ?: idx.toString()
    }
) {
    val density = LocalDensity.current
    Canvas(modifier = modifier) {
        if (values.isEmpty()) return@Canvas

        val w = size.width
        val h = size.height

        // Espaços para eixos e labels
        val leftPadding = 48.dp.toPx()   // espaço para labels de Y
        val rightPadding = 16.dp.toPx()
        val topPadding = 16.dp.toPx()
        val bottomPadding = 36.dp.toPx() // espaço para labels de X

        val chartWidth = w - leftPadding - rightPadding
        val chartHeight = h - topPadding - bottomPadding

        val minV = values.minOrNull() ?: 0f
        val maxV = values.maxOrNull() ?: 0f
        val range = (maxV - minV).coerceAtLeast(1f)

        val stepX = if (values.size > 1) chartWidth / (values.size - 1) else chartWidth

        // Mapeia valores para pontos no Canvas (invertendo Y porque 0 está no topo)
        val points = values.mapIndexed { i, v ->
            val x = leftPadding + stepX * i
            val norm = (v - minV) / range
            val y = topPadding + (1f - norm) * chartHeight
            Offset(x, y)
        }

        // Preparar Paint para texto
        val textSizePx = with(density) { 12.dp.toPx() }
        val labelPaint = Paint().apply {
            color = android.graphics.Color.parseColor("#222222")
            textSize = textSizePx
            isAntiAlias = true
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
        }

        // Desenhar eixos
        val yAxisX = leftPadding
        val xAxisY = h - bottomPadding
        drawLine(
            color = axisColor,
            start = Offset(yAxisX, topPadding),
            end = Offset(yAxisX, h - bottomPadding),
            strokeWidth = 2f
        )
        drawLine(
            color = axisColor,
            start = Offset(leftPadding, xAxisY),
            end = Offset(w - rightPadding, xAxisY),
            strokeWidth = 2f
        )

        // Ticks e labels do Y
        val yTicks = generateTicks(minV, maxV, yTickStep)
        yTicks.forEach { tickValue ->
            val norm = (tickValue - minV) / range
            val y = topPadding + (1f - norm) * chartHeight
            // Grid horizontal
            if (showGrid) {
                drawLine(
                    color = gridColor,
                    start = Offset(leftPadding, y),
                    end = Offset(w - rightPadding, y),
                    strokeWidth = 1f
                )
            }
            // Tick do eixo Y
            drawLine(
                color = axisColor,
                start = Offset(yAxisX - 6f, y),
                end = Offset(yAxisX, y),
                strokeWidth = 2f
            )
            // Label à esquerda
            val label = yLabelFormatter(tickValue)
            drawIntoCanvas { canvas ->
                val textWidth = labelPaint.measureText(label)
                canvas.nativeCanvas.drawText(
                    label,
                    yAxisX - 8f - textWidth,
                    y + labelPaint.textSize / 2f - 2f,
                    labelPaint
                )
            }
        }

        // Ticks e labels do X (por índice)
        if (values.size > 1 && xTickStep > 0) {
            for (i in 0 until values.size) {
                if (i % xTickStep == 0) {
                    val x = leftPadding + stepX * i
                    // Grid vertical
                    if (showGrid) {
                        drawLine(
                            color = gridColor,
                            start = Offset(x, topPadding),
                            end = Offset(x, h - bottomPadding),
                            strokeWidth = 1f
                        )
                    }
                    // Tick do eixo X
                    drawLine(
                        color = axisColor,
                        start = Offset(x, xAxisY),
                        end = Offset(x, xAxisY + 6f),
                        strokeWidth = 2f
                    )
                    // Label embaixo
                    val label = xLabelFormatter(i)
                    drawIntoCanvas { canvas ->
                        val textWidth = labelPaint.measureText(label)
                        canvas.nativeCanvas.drawText(
                            label,
                            x - textWidth / 2f,
                            xAxisY + 6f + labelPaint.textSize + 4f,
                            labelPaint
                        )
                    }
                }
            }
        }

        // Path suave
        val path = smoothBezierPath(points, smoothness)

        // Preenchimento sob a curva
        val fillPath = Path().apply {
            addPath(path)
            lineTo(points.last().x, xAxisY)
            lineTo(points.first().x, xAxisY)
            close()
        }

        // Preenchimento
        drawPath(path = fillPath, color = fillColor)

        // Linha principal
        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 4f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )

        // Pontos
        points.forEach { p ->
            drawCircle(color = lineColor, radius = 4f, center = p)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmoothLineChartPreview() {
    val values = listOf(
        0f, 5.4f, 2f, 6f, 9f, 4f, 2f, 4f, 8f, 1f, 11f, 3f
    ).map { it * 10f }

    SmoothLineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        values = values,
        smoothness = 0.22f,
        // Intervalo padrão: 10 em 10
        xTickStep = 10,
        yTickStep = 10f,
        // Exemplo de labels customizados para X (opcional)
        xLabels = List(values.size) { i -> "P$i" }
    )
}