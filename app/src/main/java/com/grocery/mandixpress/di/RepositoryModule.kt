package com.grocery.mandixpress.di

import com.grocery.mandixpress.features.splash.domain.repository.AuthRepository
import com.grocery.mandixpress.data.repository.AuthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun providesAuthRepository(
        repo: AuthRepositoryImpl
    ):AuthRepository

}