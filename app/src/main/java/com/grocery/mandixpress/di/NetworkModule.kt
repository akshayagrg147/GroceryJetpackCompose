package com.grocery.mandixpress.di

import android.content.Context
import androidx.room.Room
import com.grocery.mandixpress.BuildConfig
import com.grocery.mandixpress.FCMApiService
import com.grocery.mandixpress.NotificationHeader

import com.grocery.mandixpress.roomdatabase.AppDatabase
import com.grocery.mandixpress.Utils.Constants
import com.grocery.mandixpress.data.network.ApiService
import com.grocery.mandixpress.roomdatabase.Dao
import com.grocery.mandixpress.WithoutNotificationHeader
import com.grocery.mandixpress.data.network.CallingCategoryWiseData
import com.grocery.mandixpress.data.network.OAuthInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
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
    fun provideDatabaseClearer(appDatabase: AppDatabase): DatabaseClearer {
        return DatabaseClearer(appDatabase)
    }

    // Create a DatabaseClearer class
    class DatabaseClearer(private val appDatabase: AppDatabase) {
        suspend fun clearDatabase() {
            withContext(Dispatchers.IO) {
                appDatabase.clearAllTables()
            }
        }
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
    fun providesApiServicewithToken(moshi: Moshi, @WithoutNotificationHeader client: OkHttpClient): ApiService =
        Retrofit
            .Builder()
            .run {
                baseUrl( BuildConfig.base_url)
                addConverterFactory(MoshiConverterFactory.create(moshi))
                client(client)
                build()
            }.create(ApiService::class.java)

    @Provides
    @Singleton
    @FCMApiService
    fun providesApiServicewithFcmUrl(moshi: Moshi, @NotificationHeader client: OkHttpClient): ApiService =
        Retrofit
            .Builder()
            .run {
                baseUrl(Constants.BASE_URLFCM)
                addConverterFactory(MoshiConverterFactory.create(moshi))

                client(client)
                build()
            }.create(ApiService::class.java)

    @Provides
    @Singleton
    @NotificationHeader
    fun providesOkHttp(oAuthInterceptor: OAuthInterceptor, @ApplicationContext applicationContext: Context): OkHttpClient {
        val cacheDir = File(applicationContext.cacheDir, "http-cache")
        val cacheSize = 10 * 1024 * 1024
        val cache = Cache(cacheDir, cacheSize.toLong())
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val additionalHeadersInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val modifiedRequest = originalRequest.newBuilder()
                .header("Content-Type", "application/json")
                .header(
                    "Authorization",
                    "key=AAAAggbJZcU:APA91bEAccbSATPsjn9Zh1mRcnLZJgqjWzoiBFfaTa9nDHiLXAM6BAEHgA_3IZro9h44_DjJgfyAZrNqUQS4Xrfp7kESJk91RbnJkUJHxL1KaO27mJewQpUPii5MOmhBgrASzRcz2VmD"
                )
                .build()
            chain.proceed(modifiedRequest)
        }

        val okHttpClientBuilder =  OkHttpClient.Builder()
            .addInterceptor(oAuthInterceptor)
            .addInterceptor(additionalHeadersInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)


        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(interceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    @WithoutNotificationHeader
    fun providesOkHttp1(oAuthInterceptor: OAuthInterceptor, @ApplicationContext applicationContext: Context): OkHttpClient {
        val cacheDir = File(applicationContext.cacheDir, "http-cache")
        val cacheSize = 10 * 1024 * 1024
        val cache = Cache(cacheDir, cacheSize.toLong())
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(oAuthInterceptor)

            .build()
    }

}

