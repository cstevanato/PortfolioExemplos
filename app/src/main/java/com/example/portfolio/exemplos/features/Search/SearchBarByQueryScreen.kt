@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.portfolio.exemplos.features.Search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SearchBarByQueryScreen() {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
//    val suggestions = listOf("Android", "Compose", "Material 3", "SearchByQuery")

    val suggestions = remember {
        mutableStateListOf(
            "Jetpack Compose",
            "Material Design 3",
            "Kotlin Coroutines",
            "Android Architecture",
            "Room Database",
            "Navigation Component",
            "ViewModel",
            "LiveData",
            "Flow",
            "Retrofit",
            "Hilt Dependency Injection",
            "Flow1",
            "Flow2",
            "Flow3",
            "Flow4",
            "Flow5",
            "Flow6",
            "Flow7",
            "Flow8",
            "Flow9",
        )
    }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                if (!suggestions.contains(it)) suggestions.add(it)
                active = false
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text("SearchByQuery...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                if (active) {
                    Icon(
                        modifier = Modifier.clickable {
                            if (query.isNotEmpty()) query = "" else active = false
                        },
                        imageVector = Icons.Default.Close, contentDescription = null
                    )
                }
            },
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth()
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .verticalScroll(scrollState)
            ) {
                suggestions.filter { it.contains(query, ignoreCase = true) }
                    .forEach { suggestion ->
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.padding(8.dp),
                                imageVector = Icons.Default.History, contentDescription = null
                            )
                            Text(
                                text = suggestion,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        query = suggestion
                                        active = false
                                    }
                            )
                        }
                    }
            }
        }
    }
}
