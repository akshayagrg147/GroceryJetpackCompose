package com.grocery.mandixpress.Utils

import android.content.Intent
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.grocery.mandixpress.HiltApplication.Companion.context
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.Utils.Constants.Companion.ACTION_CUSTOM_BROADCAST
import com.grocery.mandixpress.common.Utils.Companion.showNotification

class MyFirebaseMessagingService : FirebaseMessagingService() {


    var shared: sharedpreferenceCommon = sharedpreferenceCommon(context!!)
    override fun onCreate() {
        super.onCreate()

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        shared.setFcmToken(token)

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val data = message.data
        val intent = Intent(ACTION_CUSTOM_BROADCAST)
        intent.putExtra("title", "${data["title"]}")
        LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
        showNotification(
            context!!,
            data["title"] ?: "",
            data["body"]?: ""
        )


    }


}