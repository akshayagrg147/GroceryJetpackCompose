package com.grocery.mandixpress.common

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import com.grocery.mandixpress.R
import com.grocery.mandixpress.features.Home.ui.screens.HomeActivity

class Utils {
    companion object{
        fun vibrator(context: Context) {
            var vibration = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

            if (Build.VERSION.SDK_INT >= 26) {
                vibration.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                val vib  = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vib.vibrate(200)
            }

        }
        fun extractSixDigitNumber(input: String): String? {
            val regex = Regex("\\b\\d{6}\\b") // Matches 6 digits surrounded by word boundaries
            val matchResult = regex.find(input)
            return matchResult?.value
        }

        fun showNotification(context: Context, message: String) {
            // declaring variables
            lateinit var notificationManager: NotificationManager
            lateinit var notificationChannel: NotificationChannel
            lateinit var builder: Notification.Builder
            val channelId = "i.apps.notifications"
            val description = "Test notification"
            notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.FLAG_IMMUTABLE
            } else {
                PendingIntent.FLAG_UPDATE_CURRENT
            }

            val pendingIntent =
                PendingIntent.getActivity(context, 0, Intent(context, HomeActivity::class.java), flag)

// RemoteViews are used to use the content of


// checking if android version is greater than oreo(API 26) or not
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationChannel =
                    NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(context, channelId)

                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(message)

                    .setContentIntent(pendingIntent)
            } else {

                builder = Notification.Builder(context)

                    .setSmallIcon(R.drawable.logo)

                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234, builder.build())
        }
    }
}