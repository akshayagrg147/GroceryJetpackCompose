package com.grocery.groceryapp.data.network

import android.util.Log
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.AppPreferenceProvider

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OAuthInterceptor @Inject constructor(
    private val sharedPreference: sharedpreferenceCommon
) : Interceptor {
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
        return chain.proceed(request)
    }


}
