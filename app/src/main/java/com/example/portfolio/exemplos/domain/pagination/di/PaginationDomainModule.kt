package com.example.portfolio.exemplos.domain.pagination.di

import com.example.portfolio.exemplos.domain.pagination.PaginationRepository
import com.example.portfolio.exemplos.domain.pagination.PaginationUseCase
import com.example.portfolio.exemplos.domain.pagination.PaginationUseCaseImpl
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