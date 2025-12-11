package com.example.portfolio.exemplos.features.pagination.domain

fun interface PaginationUseCase {
    suspend operator fun invoke(page: Int, pageSize: Int): Result<List<ListItem>>
}