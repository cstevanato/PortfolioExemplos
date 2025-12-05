package com.example.portfolio.exemplos.components

import androidx.compose.runtime.Stable

@Stable
data class CardModel(
    val title: String,
    val description: String,
    val icon: Int? = null
)
