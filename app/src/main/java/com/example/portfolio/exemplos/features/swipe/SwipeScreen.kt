package com.example.portfolio.exemplos.features.swipe

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun SwipeScreen() {
    val list = remember {
        mutableStateListOf(
            "Subscribe",
            "Like",
            "Share",
            "Comment",
            "others"
        )
    }
    val scope = rememberCoroutineScope()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        LazyColumn(
            state = rememberLazyListState(),
            contentPadding = paddingValues,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(items = list, key = { _, item ->
                item.hashCode()
            }) { index, item ->
                val dismissState = rememberSwipeToDismissBoxState()
//                var isVisible by remember { mutableStateOf(true) }

                SwipeToDismissBox(
                    state = dismissState,
                    backgroundContent = {
                        val color by animateColorAsState(
                            when (dismissState.dismissDirection) {
                                SwipeToDismissBoxValue.Settled -> Color.Transparent
                                SwipeToDismissBoxValue.StartToEnd -> Color.Green
                                SwipeToDismissBoxValue.EndToStart -> Color.Red
                            }
                        )
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.align(Alignment.CenterStart)
                            )

                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.align(Alignment.CenterEnd)
                            )
                        }
                    },
                    onDismiss = { direction ->
                        if (direction == SwipeToDismissBoxValue.EndToStart) {
//                            isVisible = false
                            list.removeAt(index)
                        } else {
                            scope.launch { dismissState.reset() }
                        }
                    },
                ) {
                    ItemUi(list = list, itemIndex = index)
                }

            }
        }
    }
}

@Composable
private fun ItemUi(
    modifier: Modifier = Modifier,
    list: SnapshotStateList<String>,
    itemIndex: Int
) {
    Card(
        modifier = modifier.padding(horizontal = 20.dp, vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp), contentAlignment = Alignment.Center
        ) {
            Text(text = list[itemIndex], fontSize = 32.sp, fontWeight = FontWeight.Bold)
        }
    }
}
