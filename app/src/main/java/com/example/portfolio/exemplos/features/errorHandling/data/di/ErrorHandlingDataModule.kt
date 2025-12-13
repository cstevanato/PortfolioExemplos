package com.example.portfolio.exemplos.features.errorHandling.data.di

import com.example.portfolio.exemplos.features.errorHandling.data.AuthRepository
import com.example.portfolio.exemplos.features.errorHandling.data.AuthRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ErrorHandlingDataModule {

    @Singleton
    @Provides
    fun provideAuthRepository(
        httpClient: HttpClient
    ): AuthRepository {
        return AuthRepositoryImpl(httpClient)
    }

}