package com.example.portfolio.exemplos.features.flowTesting.data.di

import com.example.portfolio.exemplos.features.flowTesting.data.AddressRepository
import com.example.portfolio.exemplos.features.flowTesting.data.AddressRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FlowTestingDataModel {
    @Singleton
    @Provides
    fun provideAddressRepository(
        httpClient: HttpClient
    ): AddressRepository {
        return AddressRepositoryImpl(httpClient)
    }

}