package com.grocery.groceryapp.data.network

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import com.grocery.groceryapp.LoginActivity
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.AppPreferenceProvider
import dagger.hilt.android.qualifiers.ApplicationContext

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

    class OAuthInterceptor @Inject constructor(
        private val sharedPreference: sharedpreferenceCommon,@ApplicationContext context: Context
    ) : Interceptor {
        val mContext=context
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            val jwtToken = sharedPreference.getJwtToken()

            // Logging the JWT token
            Log.d("main", "JWT Token: $jwtToken")

            // Include the Bearer token if available
            if (jwtToken.isNotEmpty()) {
                request = request.newBuilder()
                    .header("Authorization", "Bearer $jwtToken")
                    .build()
            }
            val response = chain.proceed(request)
            if (response.code == 401) {
                redirectToLoginScreen(mContext)

            }
            return response
        }
        private fun redirectToLoginScreen(context: Context) {
            sharedPreference.clearSharePreference()
            val loginIntent = Intent(context, LoginActivity::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(context,loginIntent,null)
        }



    }
