package com.example.portfolio.exemplos.features.pagination

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}