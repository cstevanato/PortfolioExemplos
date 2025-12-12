package com.example.portfolio.exemplos.features.media.di

import android.content.Context
import com.example.portfolio.exemplos.features.media.MediaReader
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object MediaModule {

    @Provides
    @Singleton
    fun provideMediaReader(
        @ApplicationContext context: Context
    ): MediaReader = MediaReader(context)

}