@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.portfolio.exemplos.features.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch


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
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                        text = it
                    )
                }
            }
        }
    }
}

