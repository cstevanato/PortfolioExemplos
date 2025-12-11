package com.example.portfolio.exemplos.core.di

import android.content.Context
import com.example.portfolio.exemplos.core.connectivity.ConnectivityObserver
import com.example.portfolio.exemplos.core.connectivity.NetworkConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object ConnectivityModule {

    @Provides
    @Singleton
    fun provideConnectivityObserver(@ApplicationContext app: Context): ConnectivityObserver {
        return NetworkConnectivityObserver(app)
    }

}