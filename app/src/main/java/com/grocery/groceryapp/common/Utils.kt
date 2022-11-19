package com.grocery.groceryapp.common

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

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
    }
}