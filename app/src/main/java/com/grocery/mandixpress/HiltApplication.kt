package com.grocery.mandixpress

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import com.google.android.libraries.places.api.Places
import com.google.firebase.messaging.FirebaseMessaging
import com.grocery.mandixpress.Utils.Constants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, BuildConfig.api_key)
        instance = this
        createNotificationChannel()
        subscribeTopic()

    }
    companion object {
        var instance: HiltApplication? = null
            private set

        @JvmStatic
        val context: Context?
            get() = instance
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                Constants.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Just to send notification, you know!"
                enableLights(true)
                lightColor = Color.RED
            }
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }

    private fun subscribeTopic() {
        FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC)
    }

}