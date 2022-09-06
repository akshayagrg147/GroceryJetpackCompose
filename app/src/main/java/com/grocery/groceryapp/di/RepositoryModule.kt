package com.grocery.groceryapp.di

import com.grocery.groceryapp.features.Spash.domain.repository.AuthRepository
import com.grocery.groceryapp.data.repository.AuthRepositoryImpl
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