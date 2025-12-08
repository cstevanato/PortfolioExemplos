package com.example.portfolio.exemplos.domain.pagination

interface PaginationRepository {
   suspend fun getItems(page: Int, pageSize: Int): Result<List<ListItem>>
}