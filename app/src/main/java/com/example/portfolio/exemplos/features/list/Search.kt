@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.portfolio.exemplos.features.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

//val suggestions = persistentListOf(
//        "Jetpack Compose",
//        "Material Design 3",
//        "Kotlin Coroutines",
//        "Android Architecture",
//        "Room Database",
//        "Navigation Component",
//        "ViewModel",
//        "LiveData",
//        "Flow",
//        "Retrofit",
//        "Hilt Dependency Injection"
//)


@OptIn(ExperimentalMaterial3Api::class)
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

@Composable
fun SearchBarByStateScreen() {

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
    val searchBarState = rememberSearchBarState()
    val textFieldState = rememberTextFieldState()
    val scope = rememberCoroutineScope()

    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                modifier = Modifier,
                searchBarState = searchBarState,
                textFieldState = textFieldState,
                onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
                placeholder = { Text("SearchByQuery...") },
                leadingIcon = {
                    if (searchBarState.currentValue == SearchBarValue.Expanded) {
                        TooltipBox(
                            positionProvider =
                                TooltipDefaults.rememberTooltipPositionProvider(
                                    TooltipAnchorPosition.Above
                                ),
                            tooltip = { PlainTooltip { Text("Back") } },
                            state = rememberTooltipState(),
                        ) {
                            IconButton(
                                onClick = { scope.launch { searchBarState.animateToCollapsed() } }
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = "Back",
                                )
                            }
                        }
                    } else {
                        Icon(Icons.Default.Search, contentDescription = null)
                    }
                },
                trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
            )
        }
    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        SearchBar(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(),
            state = searchBarState, inputField = inputField
        )
        ExpandedFullScreenSearchBar(state = searchBarState, inputField = inputField) {
            LazyColumn {
                items(
                    items = suggestions.filter {
                        it.contains(
                            textFieldState.text,
                            ignoreCase = true
                        )
                    },
                    key = { it }
                ) { it ->
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                        text = it
                    )
                }
            }
        }
    }
}

