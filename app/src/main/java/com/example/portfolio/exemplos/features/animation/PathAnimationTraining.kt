package com.example.portfolio.exemplos.features.animation


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StampedPathEffectStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.vector.toPath
import org.checkerframework.checker.units.qual.degrees
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

// https://medium.com/androiddevelopers/playing-with-paths-3fbc679a6f77

@Preview
@Composable
fun PathTraining() {
    val path = Path()

    path.moveTo(30f, 30f)
    path.lineTo(30f, 70f)
    path.lineTo(70f, 70f)
    path.relativeLineTo(-20f, -20f)
    path.relativeLineTo(20f, -20f)
    path.close()

    Canvas(modifier = Modifier.size(80.dp, 80.dp)) {
        drawPath(
            path = path,
            color = Color.Blue,
            style = Fill //Stroke(width = 2f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }

}

@Preview
@Composable
fun PathTraining1() {
    val path = Path()

    path.moveTo(30f, 30f)
    path.lineTo(30f, 70f)
    path.lineTo(70f, 70f)
    path.relativeLineTo(-20f, -20f)
    path.relativeLineTo(20f, -20f)
    path.close()

    Canvas(modifier = Modifier.size(80.dp, 80.dp)) {
        drawPath(
            path = path,
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFBF00FF), Color(0xFF0000FF), Color(0xFF0000FF))
            ),
            style = Fill //Stroke(width = 2f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }

}

@Preview
@Composable
fun PathTraining2() {
    val path = Path()

    path.moveTo(30f, 30f)
    path.lineTo(30f, 70f)
    path.lineTo(70f, 70f)
    path.relativeLineTo(-20f, -20f)
    path.relativeLineTo(20f, -20f)
    path.close()

    Canvas(modifier = Modifier.size(80.dp, 80.dp)) {
        drawPath(
            path = path,
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFBF00FF), Color(0xFF0000FF), Color(0xFF0000FF))
            ),
            style = Stroke(width = 2f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }

}

@Preview
@Composable
fun PathTraining3() {
    val path = Path()

    path.moveTo(30f, 30f)
    path.lineTo(30f, 70f)
    path.lineTo(70f, 70f)
    path.relativeLineTo(-20f, -20f)
    path.relativeLineTo(20f, -20f)
    path.close()

    Canvas(modifier = Modifier.size(80.dp, 80.dp)) {
        drawPath(
            path = path,
            brush = Brush.horizontalGradient(
                colors = listOf(Color(0xFFBF00FF), Color(0xFF0000FF), Color(0xFF0000FF))
            ),
            style = Stroke(
                width = 5f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(
                        7f,
                        7f
                    )
                )
            )
        )
    }

}


@Preview
@Composable
fun QuadraticTraining1() {
    val path = Path()
    path.moveTo(10f, 60f)
    path.quadraticTo(x1 = 50f, y1 = 20f, x2 = 90f, y2 = 60f)

    Canvas(modifier = Modifier.size(80.dp, 80.dp)) {
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 2f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

/**
 * ![System Diagram](docs/images/image_cub.png)
 */

@Preview
@Composable
fun CubicTraining1() {
    val path = Path()
    path.moveTo(10f, 60f)
    path.cubicTo(
        x1 = 25f, y1 = 20f,
        x2 = 70f, y2 = 80f,
        x3 = 90f, y3 = 60f,
    )

    Canvas(modifier = Modifier.size(80.dp, 80.dp)) {
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 2f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Preview
@Composable
fun ArcTraining1() {
    val path = Path()
    path.addArc(
        oval = Rect(center = Offset(40f, 40f), radius = 30f),
        startAngleDegrees = -10f,
        sweepAngleDegrees = 220f
    )

    Canvas(modifier = Modifier.size(80.dp, 80.dp)) {
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 2f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Preview
@Composable
fun RectTraining1() {
    val path = Path()
    path.addRect(
        rect = Rect(center = Offset(40f, 40f), radius = 30f),
    )

    Canvas(modifier = Modifier.size(80.dp, 80.dp)) {
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 2f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}

@Preview
@Composable
fun OvalTraining1() {
    val path = Path()
    path.addOval(
        oval = Rect(
            topLeft = Offset(20f, 20f),
            bottomRight = Offset(30f, 70f)
        ),
    )

    Canvas(modifier = Modifier.size(80.dp, 80.dp)) {
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 2f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}


@Preview
@Composable
fun SmileyFaceFromPathData() {
    val path = Path()

    // Face outline
    path.addOval(Rect(left = 5f, top = 5f, right = 75f, bottom = 75f))

    // Eyes
    path.addOval(Rect(left = 24f, top = 26f, right = 32f, bottom = 34f))
    path.addOval(Rect(left = 48f, top = 26f, right = 56f, bottom = 34f))

    // Mouth using SVG path data
    // The string is from an SVG <path> d attribute, scaled for an 80x80 viewport
    val mouthPathData = "M 24,48 A 16,16 0 0,0 56,48"
    val mouthPath = PathParser().parsePathString(mouthPathData).toPath()
    path.addPath(mouthPath)

    Canvas(modifier = Modifier.size(80.dp)) {
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(width = 2f, cap = StrokeCap.Round, join = StrokeJoin.Round)
        )
    }
}


@Preview
@Composable
fun NodesTraining1() {
    val pathData =
        // Face (círculo com dois arcos)
        "M 50 10 " +
                "A 40 40 0 1 1 50 90 " +
                "A 40 40 0 1 1 50 10 Z " +

                // Olho esquerdo (círculo via dois arcos)
                "M 30 40 " +
                "a 5 5 0 1 0 10 0 " +
                "a 5 5 0 1 0 -10 0 " +

                // Olho direito (círculo via dois arcos)
                "M 60 40 " +
                "a 5 5 0 1 0 10 0 " +
                "a 5 5 0 1 0 -10 0 " +

                // Boca (curva cúbica para sorrir)
                "M 32 60 " +
                "C 42 75, 58 75, 68 60"
    val path = PathParser().parsePathString(pathData).toPath()

    Canvas(modifier = Modifier.size(40.dp)) {
        withTransform({
            val sx = size.width / 100f
            val sy = size.height / 100f
            scale(scaleX = sx, scaleY = sy)
        }) {
            drawPath(
                path = path,
                color = Color.Blue,
                style = Stroke(width = 3f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
    }
}


@Preview
@Composable
fun HexagonShape(modifier: Modifier = Modifier, color: Color = Color.Blue) {
    Canvas(modifier = modifier.size(200.dp)) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val center = Offset(canvasWidth / 2, canvasHeight / 2)
        val radius = size.minDimension / 2

        val hexagonPath = Path().apply {
            // Hexágono tem 6 lados, 360 graus / 6 = 60 graus (π/3 radianos)
            for (i in 0..5) {
                val angle = Math.toRadians(60.0 * i - 30.0) // -30 para ponta para cima
                val x = center.x + radius * cos(angle).toFloat()
                val y = center.y + radius * sin(angle).toFloat()
                if (i == 0) moveTo(x, y) else lineTo(x, y)
            }
            close()
        }

        // Desenhar Preenchido
//        drawPath(path = hexagonPath, color = color)

        // Ou Desenhar Borda (Stroke)
        drawPath(path = hexagonPath, color = color, style = Stroke(width = 5f))
    }
}


@Preview
@Composable
fun StarGradientCanvas() {
    Canvas(modifier = Modifier.size(200.dp)) {
        val path = Path()
        val cx = size.width / 2
        val cy = size.height / 2
        val outerRadius = size.minDimension / 2
        val innerRadius = outerRadius / 2.5f
        val numPoints = 5

        // Cálculos trigonométricos para desenhar a estrela
        for (i in 0 until numPoints * 2) {
            val angle = PI / numPoints * i - PI / 2
            val radius = if (i % 2 == 0) outerRadius else innerRadius
            val x = cx + radius * cos(angle).toFloat()
            val y = cy + radius * sin(angle).toFloat()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }
        path.close()

        // Definição do Gradiente Azul -> Vermelho
        val gradientBrush = Brush.linearGradient(
            colors = listOf(Color.Blue, Color.Red),
            start = Offset(0f, 0f),
            end = Offset(size.width, size.height)
        )

        // Desenhar a estrela preenchida
        drawPath(
            path = path,
            brush = gradientBrush,
            style = Fill
        )
    }
}

@Preview
@Composable
fun TrianguloRetanguloCentralizado() {
    Canvas(
        modifier = Modifier
            .size(200.dp)
            .padding(20.dp) // Aplica o padding de 20dp ao redor
    ) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            // Vértice superior esquerdo
            moveTo(0f, 0f)
            // Vértice inferior esquerdo (ângulo reto)
            lineTo(0f, height)
            // Vértice inferior direito
            lineTo(width, height)
            // Fecha o caminho de volta para o superior esquerdo
            close()
        }

        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(
                width = 25f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(
                        10f,
                        30f
                    )
                )
            )
        )

    }
}

@Preview
@Composable
fun TrianguloRetanguloEffect() {
    Canvas(
        modifier = Modifier
            .size(200.dp)
            .padding(20.dp) // Aplica o padding de 20dp ao redor
    ) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            // Vértice superior esquerdo
            moveTo(0f, 0f)
            // Vértice inferior esquerdo (ângulo reto)
            lineTo(0f, height)
            // Vértice inferior direito
            lineTo(width, height)
            // Fecha o caminho de volta para o superior esquerdo
            close()
        }

        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(
                width = 25f,
                cap = StrokeCap.Round,
                join = StrokeJoin.Round,
                pathEffect = PathEffect.cornerPathEffect(
                    radius = 18f
                )
            )
        )

    }
}

@Preview
@Composable
fun QuadraticTrainingEffect() {
    val path = Path()
    path.moveTo(10f, 75f)
    path.quadraticTo(
        x1 = 50f, y1 = -50f,
        x2 = 90f, y2 = 75f
    )

    val shape = Path().apply {
        addOval(
            oval = Rect(Offset.Zero, 2f)
        )
    }

    Canvas(modifier = Modifier.size(80.dp, 80.dp)) {
        drawPath(
            path = path,
            color = Color.Blue,
            style = Stroke(

                pathEffect = PathEffect.stampedPathEffect(
                    shape = shape,
                    advance = 7f,
                    phase = 364f,
                    style = StampedPathEffectStyle.Translate
                )
            )
        )
    }
}
