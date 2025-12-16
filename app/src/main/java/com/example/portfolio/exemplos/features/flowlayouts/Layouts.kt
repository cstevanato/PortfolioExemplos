package com.example.portfolio.exemplos.features.flowlayouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random


@Composable
fun FlowRowExample() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->

        FlowRow(modifier = Modifier.padding(paddingValues)) {
            repeat(50) {
                Box(
                    modifier = Modifier
                        .width(Random.nextInt(50, 200).dp)
                        .height(100.dp)
                        .background(Color(Random.nextLong(0xFFFFFFFF)))
                )
            }
        }
    }
}

@Composable
fun FlowRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        measurePolicy = { measurables, constraints ->
            val placeables = measurables.map {
                it.measure(constraints)
            }
            val groupPlaceables = mutableListOf<List<Placeable>>()
            var currentGroup = mutableListOf<Placeable>()
            var currentGroupWidth = 0
            placeables.forEach { placeable ->
                if (currentGroupWidth + placeable.width <= constraints.maxWidth) {
                    currentGroup.add(placeable)
                    currentGroupWidth += placeable.width
                } else {
                    groupPlaceables.add(currentGroup)
                    currentGroup = mutableListOf(placeable)
                    currentGroupWidth = placeable.width
                }
            }
            if (currentGroup.isNotEmpty()) {
                groupPlaceables.add(currentGroup)
            }
            layout(width = constraints.maxWidth, height = constraints.maxHeight) {
                var yPosition = 0
                groupPlaceables.forEach { row ->
                    var xPosition = 0
                    row.forEach { placeable ->
                        placeable.place(
                            x = xPosition,
                            y = yPosition
                        )
                        xPosition += placeable.width // + horizontalGap.roundToPx()
                    }
                    yPosition += row.maxOfOrNull { it.height } ?: 0 // + verticalGap.roundToPx()
                }
            }
        },
        content = content,
    )

}


@Composable
fun CustomColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        // Medir os filhos
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        // Calcular altura total
        val height = placeables.sumOf { it.height }
        val width = placeables.maxOfOrNull { it.width } ?: 0

        // Posicionar os filhos
        layout(width, height) {
            var yPosition = 0
            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += placeable.height
            }
        }
    }
}

// Uso
@Composable
fun CustomColumnExample() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->

        CustomColumn(modifier = Modifier.padding(paddingValues)) {
            Text("Item 1")
            Text("Item 2")
            Text("Item 3")
        }
    }
}

@Composable
fun CustomGrid(
    columns: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val columnWidth = constraints.maxWidth / columns

        val itemConstraints = constraints.copy(
            minWidth = 0,
            maxWidth = columnWidth
        )

        // Medir todos os filhos
        val placeables = measurables.map { measurable ->
            measurable.measure(itemConstraints)
        }

        // Agrupar por linhas
        val rows = placeables.chunked(columns)
        val rowHeights = rows.map { row ->
            row.maxOfOrNull { it.height } ?: 0
        }

        val height = rowHeights.sum()
        val width = constraints.maxWidth

        // Posicionar
        layout(width, height) {
            var yPosition = 0
            rows.forEachIndexed { rowIndex, row ->
                var xPosition = 0
                row.forEach { placeable ->
                    placeable.placeRelative(x = xPosition, y = yPosition)
                    xPosition += columnWidth
                }
                yPosition += rowHeights[rowIndex]
            }
        }
    }
}

// Uso
@Composable
fun GridExample() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->

        CustomGrid(columns = 3, modifier = Modifier.padding(paddingValues)) {
            repeat(9) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(Color.Blue)
                        .aspectRatio(1f)
                ) {
                    Text("$index", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CircularLayout(
    modifier: Modifier = Modifier,
    radius: Dp = 100.dp,
    content: @Composable () -> Unit
) {

    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val radiusPx = radius.toPx()

        // Medir os filhos
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
        }

        val size = (radiusPx * 2).toInt()

        layout(size, size) {
            val angleStep = 2 * PI / placeables.size

            placeables.forEachIndexed { index, placeable ->
                val angle = index * angleStep - PI / 2 // Começar do topo

                val x = (radiusPx + radiusPx * cos(angle) - placeable.width / 2).toInt()
                val y = (radiusPx + radiusPx * sin(angle) - placeable.height / 2).toInt()

                placeable.placeRelative(x, y)
            }
        }
    }
}

// Uso
@Composable
fun CircularExample() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        CircularLayout(radius = 120.dp, modifier = Modifier.padding(paddingValues)) {
            repeat(8) { index ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Red, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text("$index", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun CascadeLayout(
    modifier: Modifier = Modifier,
    offset: Dp = 16.dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val offsetPx = offset.toPx().toInt()

        val placeables = measurables.map { measurable ->
            measurable.measure(constraints.copy(minWidth = 0, minHeight = 0))
        }

        val totalOffset = offsetPx * (placeables.size - 1)
        val width = (placeables.maxOfOrNull { it.width } ?: 0) + totalOffset
        val height = (placeables.maxOfOrNull { it.height } ?: 0) + totalOffset

        layout(width, height) {
            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(
                    x = index * offsetPx,
                    y = index * offsetPx
                )
            }
        }
    }
}

// Uso
@Composable
fun CascadeExample() {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize(),
    ) { paddingValues ->
        CascadeLayout(offset = 20.dp, modifier = Modifier.padding(paddingValues)) {
            repeat(5) { index ->
                Card(
                    modifier = Modifier.size(100.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF6200EE)
                    )
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Card $index", color = Color.White)
                    }
                }
            }
        }
    }
}