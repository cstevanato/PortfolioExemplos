package com.example.portfolio.exemplos.features.pagination.ui

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}