package com.example.portfolio.exemplos.features.pagination.data.repository

import com.example.portfolio.exemplos.features.pagination.domain.ListItem
import com.example.portfolio.exemplos.features.pagination.domain.PaginationRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class PaginationRepositoryImpl @Inject constructor() : PaginationRepository {

    private val remoteDataSource = (1..100).map {
        ListItem(
            title = "Item $it",
            description = "Description $it"
        )
    }

    override suspend fun getItems(
        page: Int,
        pageSize: Int
    ): Result<List<ListItem>> {
        delay(2000L)
        val startingIndex = page * pageSize
        return if (startingIndex + pageSize <= remoteDataSource.size) {
            Result.success(
                remoteDataSource.slice(startingIndex until startingIndex + pageSize)
            )
        } else Result.success(emptyList())
    }


}