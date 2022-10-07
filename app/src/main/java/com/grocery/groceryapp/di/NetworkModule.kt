package com.grocery.groceryapp.di

import android.content.Context
import androidx.room.Room
import com.grocery.groceryapp.RoomDatabase.AppDatabase
import com.grocery.groceryapp.Utils.Constants
import com.grocery.groceryapp.data.network.ApiService
import com.grocery.groceryapp.RoomDatabase.Dao
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "your_db_name"
    ).build()
        @Provides
        @Singleton
        fun provideChannelDao(appDatabase: AppDatabase): Dao {
            return appDatabase.channelDao()
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