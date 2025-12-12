@file:OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)

package com.example.portfolio.exemplos.features.media

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Audiotrack
import androidx.compose.material.icons.filled.VideoFile
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@Composable
fun MediaScreen() {


    Scaffold(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = "Media")
            })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            PermissionDeniedScreen(
                modifier = Modifier.padding(paddingValues)
            ) {
                MediaList()
            }
        }
    }
}

@Composable
private fun PermissionDeniedScreen(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val permissionsState = rememberMultiplePermissionsState(
        permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(
                android.Manifest.permission.READ_MEDIA_AUDIO,
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.READ_MEDIA_VIDEO
            )
        } else {
            listOf(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    )
    when {
        permissionsState.allPermissionsGranted -> {
            content.invoke()
        }

        else -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { permissionsState.launchMultiplePermissionRequest() }) {
                    Text("Solicitar Permissões")
                }
            }
        }
    }
}


@Composable
private fun MediaList(viewModel: MediaViewModel = hiltViewModel()) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(items = viewModel.files) {
            MediaListItem(
                file = it,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

}

@Composable
private fun MediaListItem(
    file: MediaFile,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (file.type) {
            MediaType.AUDIO -> {
                Image(
                    imageVector = Icons.Default.Audiotrack,
                    contentDescription = null,
                    modifier = Modifier.width(100.dp)
                )
            }

            MediaType.IMAGE -> {
                AsyncImage(
                    model = file.uri,
                    contentDescription = null,
                    modifier = Modifier.width(100.dp)
                )
            }

            MediaType.VIDEO -> {
                Image(
                    imageVector = Icons.Default.VideoFile,
                    contentDescription = null,
                    modifier = Modifier.width(100.dp)
                )
            }
        }
        Text(
            "${file.name} - ${file.type}", modifier
                .padding(16.dp)
                .weight(1f)
        )
    }
}