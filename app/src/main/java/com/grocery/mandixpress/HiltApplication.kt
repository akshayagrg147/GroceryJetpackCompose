package com.grocery.mandixpress

import android.app.Application
import android.content.Context
import com.google.android.libraries.places.api.Places
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication:Application() {

    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, "AIzaSyBmYp6mt0CNOiZALzbN10jwBxgN6n2E8-U")
        instance = this

    }
    companion object {
        var instance: HiltApplication? = null
            private set

        @JvmStatic
        val context: Context?
            get() = instance
    }

}