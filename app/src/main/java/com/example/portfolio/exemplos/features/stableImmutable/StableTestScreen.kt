package com.example.portfolio.exemplos.features.stableImmutable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.skydoves.compose.stability.runtime.TraceRecomposition
import kotlinx.collections.immutable.persistentListOf


@Stable
data class ContactListState(
    val isLoading: Boolean,
    val names: List<String>
)

@Composable
fun StableScreen() {
    var selected by remember { mutableStateOf(false) }
    var listContact by remember { mutableStateOf(persistentListOf("Pedro")) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Checkbox(
                checked = selected,
                onCheckedChange = { selected = it }
            )
            Button(
                onClick = {   listContact = listContact.add("${listContact[0]} ${listContact.size}") }
            ) {
              Text("Adicionar elemmento")
            }
            ContactList(
                ContactListState(
                    isLoading = false,
                    names = listContact
                )
            )
        }
    }
}


@TraceRecomposition
@Composable
fun ContactList(data: ContactListState) {
    Box(contentAlignment = Alignment.Center) {
        if (data.isLoading) {
            CircularProgressIndicator()
        } else {
            Text(data.names.toString())
        }
    }
}

