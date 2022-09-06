package com.grocery.groceryapp.di

import com.grocery.groceryapp.Utils.Constants
import com.grocery.groceryapp.data.network.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providesMoshi(): Moshi =
        Moshi
            .Builder()
            .run {
                add(KotlinJsonAdapterFactory())
                build()
            }

    @Provides
    @Singleton
    fun providesApiServicewithToken(moshi: Moshi): ApiService =
        Retrofit
            .Builder()
            .run {
                baseUrl(Constants.AppUrl)
                addConverterFactory(MoshiConverterFactory.create(moshi))
              //  client(client)
                build()
            }.create(ApiService::class.java)
}