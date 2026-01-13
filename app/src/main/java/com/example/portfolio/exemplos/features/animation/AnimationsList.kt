package com.example.portfolio.exemplos.features.animation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun AnimationsListScreen(modifier: Modifier = Modifier) {
    val animatingList = remember {
        mutableStateListOf<String>()
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(vertical = 15.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { animatingList.add(0, "Item - ${Random.nextInt()}") }
                ) {
                    Text("Add")
                }
                Button(
                    onClick = { animatingList.shuffle() }
                ) {
                    Text("Shuffle")
                }

                Button(
                    onClick = { animatingList.removeAt(0) }
                ) {
                    Text("Delete")
                }
            }
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)

        ) {
            items(animatingList, key = { it }) { str ->
                ListItem(
                    headlineContent = { Text(text = str) },
                    modifier = Modifier
                        .animateItem()
                        .clip(RoundedCornerShape(10.dp)),
                    colors = ListItemDefaults.colors(containerColor = MaterialTheme.colorScheme.primary)
                )
            }
        }

    }
}