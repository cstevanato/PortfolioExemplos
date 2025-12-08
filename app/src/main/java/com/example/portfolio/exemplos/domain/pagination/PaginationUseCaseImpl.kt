package com.example.portfolio.exemplos.domain.pagination

import javax.inject.Inject

class PaginationUseCaseImpl @Inject constructor(
    private val paginationRepository: PaginationRepository
) : PaginationUseCase {
    override suspend fun invoke(
        page: Int,
        pageSize: Int
    ): Result<List<ListItem>> {
        return paginationRepository.getItems(page, pageSize)
    }


}