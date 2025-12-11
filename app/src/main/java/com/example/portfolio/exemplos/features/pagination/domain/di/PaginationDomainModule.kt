package com.example.portfolio.exemplos.features.pagination.domain.di

import com.example.portfolio.exemplos.features.pagination.domain.PaginationRepository
import com.example.portfolio.exemplos.features.pagination.domain.PaginationUseCase
import com.example.portfolio.exemplos.features.pagination.domain.PaginationUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PaginationDomainModule {
    @Provides
    @Singleton
    fun providePaginationUseCase(
        paginationRepository: PaginationRepository
    ): PaginationUseCase = PaginationUseCaseImpl(
        paginationRepository = paginationRepository
    )

}