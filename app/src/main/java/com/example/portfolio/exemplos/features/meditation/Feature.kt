package com.example.portfolio.exemplos.features.meditation


import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class Feature(
    val title: String,
    val iconId: Int,
    val lightColor: Color,
    val mediumColor: Color,
    val darkColor: Color
)
