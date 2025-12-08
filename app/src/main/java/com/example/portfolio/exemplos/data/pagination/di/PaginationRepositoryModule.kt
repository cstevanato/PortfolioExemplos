package com.example.portfolio.exemplos.data.pagination.di

import com.example.portfolio.exemplos.data.pagination.repository.PaginationRepositoryImpl
import com.example.portfolio.exemplos.domain.pagination.PaginationRepository
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