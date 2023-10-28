package com.grocery.mandixpress

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenApiService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NotificationHeader

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WithoutNotificationHeader


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CommonApiService

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FCMApiService