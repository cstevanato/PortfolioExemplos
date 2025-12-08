package com.example.portfolio.exemplos.domain.pagination

fun interface PaginationUseCase {
    suspend operator fun invoke(page: Int, pageSize: Int): Result<List<ListItem>>
}