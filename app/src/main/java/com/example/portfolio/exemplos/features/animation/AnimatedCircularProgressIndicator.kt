import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.portfolio.exemplos.features.animation.AnimatedCircularProgress
import com.example.portfolio.exemplos.ui.theme.PortfolioExemplosTheme
import kotlin.math.roundToInt

@Composable
fun AnimatedCircularProgressIndicator(
    progress: Float,                 // valor alvo entre 0f..1f
    durationMillis: Int = 2000,      // duração da animação
    modifier: Modifier = Modifier,
    showLabel: Boolean = true
) {
    val clampedTarget = progress.coerceIn(0f, 1f)

    val animatedProgress by animateFloatAsState(
        targetValue = clampedTarget,
        animationSpec = tween(
            durationMillis = durationMillis,
            easing = FastOutSlowInEasing
        ),
        label = "circular-progress"
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            strokeWidth = 8.dp,
            modifier = Modifier.fillMaxSize(),
            progress = animatedProgress,

        )
        if (showLabel) {
            Text(
                text = "${(animatedProgress * 100).roundToInt()}%",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedCircularProgressIndicatorPreview() {
    PortfolioExemplosTheme {
        AnimatedCircularProgressIndicator(
            progress = 0.75f,
            durationMillis = 2000,
            modifier = Modifier.size(120.dp)
        )
    }
}


@Composable
fun AnimatedCircularProgressIndicatorScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.size(300.dp), contentAlignment = Alignment.Center) {
            AnimatedCircularProgressIndicator(
                progress = 0.75f,
                durationMillis =4000,
                modifier = Modifier.size(400.dp)
            )
        }
    }
}

// Exemplo de uso:
// AnimatedCircularProgressIndicator(
//     progress = 0.75f,
//     durationMillis = 2000,
//     modifier = Modifier.size(120.dp)
// )