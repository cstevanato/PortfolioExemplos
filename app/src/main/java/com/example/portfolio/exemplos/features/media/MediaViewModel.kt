package com.example.portfolio.exemplos.features.media

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.portfolio.exemplos.core.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MediaViewModel @Inject constructor(
    private val mediaReader: MediaReader,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    var files by mutableStateOf<List<MediaFile>>(emptyList())
        private set

    init {
        viewModelScope.launch(dispatcher) {
            files = mediaReader.getAllMediaFiles()
        }
    }
}