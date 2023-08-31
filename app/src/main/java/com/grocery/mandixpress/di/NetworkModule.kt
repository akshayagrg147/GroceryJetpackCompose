package com.grocery.mandixpress.di

import android.content.Context
import androidx.room.Room

import com.grocery.mandixpress.RoomDatabase.AppDatabase
import com.grocery.mandixpress.Utils.Constants
import com.grocery.mandixpress.data.network.ApiService
import com.grocery.mandixpress.RoomDatabase.Dao
import com.grocery.mandixpress.data.network.CallingCategoryWiseData
import com.grocery.mandixpress.data.network.OAuthInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    fun provideCallingCategoryWiseData(): CallingCategoryWiseData =CallingCategoryWiseData()


    @Provides
    @Singleton
    fun providesApiServicewithToken(moshi: Moshi, client: OkHttpClient): ApiService =
        Retrofit
            .Builder()
            .run {
                baseUrl(Constants.AppUrl)
                addConverterFactory(MoshiConverterFactory.create(moshi))
                client(client)
                build()
            }.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesOkHttp(oAuthInterceptor: OAuthInterceptor): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(
                oAuthInterceptor
            )
            .build()
    }
}