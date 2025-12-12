package com.example.portfolio.exemplos.features.media

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaReader: MediaReader
) : ViewModel() {

    var files by mutableStateOf<List<MediaFile>>(emptyList())
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            files = mediaReader.getAllMediaFiles()
        }
    }
}