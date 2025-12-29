package com.example.portfolio.exemplos.core.navigation.di

import com.example.portfolio.exemplos.Route
import com.example.portfolio.exemplos.core.navigation.DefaultNavigator
import com.example.portfolio.exemplos.core.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationCoreModule {

    @Provides
    @Singleton
    fun provideNavigator(): Navigator = DefaultNavigator(startDestination = Route.MainHome.Home)

}