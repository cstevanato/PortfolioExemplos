package com.example.portfolio.exemplos

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigateViewModel @Inject constructor() : ViewModel() {
    val backStack = mutableStateListOf<Dest>(Dest.Home)
}