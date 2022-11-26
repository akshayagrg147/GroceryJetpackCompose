package com.grocery.groceryapp

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication:Application() {

    override fun onCreate() {
        super.onCreate()
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