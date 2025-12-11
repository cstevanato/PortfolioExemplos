package com.example.portfolio.exemplos.features.pagination.domain

interface PaginationRepository {
   suspend fun getItems(page: Int, pageSize: Int): Result<List<ListItem>>
}