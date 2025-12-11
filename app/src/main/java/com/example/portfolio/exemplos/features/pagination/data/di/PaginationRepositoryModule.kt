package com.example.portfolio.exemplos.features.pagination.data.di

import com.example.portfolio.exemplos.features.pagination.data.repository.PaginationRepositoryImpl
import com.example.portfolio.exemplos.features.pagination.domain.PaginationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PaginationRepositoryModule {
    @Provides
    @Singleton
    fun providePaginationRepository(
    ) : PaginationRepository = PaginationRepositoryImpl()

}