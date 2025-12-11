package com.example.portfolio.exemplos.features.pagination.domain

import androidx.compose.runtime.Stable

@Stable
data class ListItem(
    val title: String,
    val description: String
)