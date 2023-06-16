package com.jskaleel.fte.domain.di

import com.jskaleel.fte.domain.usecase.MainActivityUseCase
import com.jskaleel.fte.domain.usecase.MainActivityUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun getMainActivityUseCase(
        mainActivityUseCaseImpl: MainActivityUseCaseImpl,
    ): MainActivityUseCase
}