package com.example.portfolio.exemplos.features.errorHandling.domain.di

import com.example.portfolio.exemplos.features.errorHandling.domain.UserDataValidator
import com.example.portfolio.exemplos.features.errorHandling.domain.UserDataValidatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
object ErrorHandlingDomainModule {
    @Provides
    @ViewModelScoped
    fun provideUserDataValidator(): UserDataValidator = UserDataValidatorImpl()
}

