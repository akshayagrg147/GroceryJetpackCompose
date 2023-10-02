package com.grocery.mandixpress.Utils

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle incoming FCM message here
        val notificationMessage = remoteMessage.notification?.body ?: "No message"
        Log.d("noficationMessage", "${notificationMessage}")
        // Notify your Composable with the received message
        // You can use LiveData, ViewModel, etc., to pass the message to your Composable
    }
}
