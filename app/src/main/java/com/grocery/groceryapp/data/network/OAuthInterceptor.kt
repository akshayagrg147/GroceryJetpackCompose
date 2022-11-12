package com.grocery.groceryapp.data.network

import android.util.Log
import com.grocery.groceryapp.Utils.AppPreferenceProvider

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class OAuthInterceptor @Inject constructor(
    private val appPreferenceProvider: AppPreferenceProvider
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization",
            "Bearer ${
//                runBlocking {
////                    preferenceStore.getPref(PreferenceStore.token1).first()
////                        .also {
////                            Log.d("main", "intercept: $it")
////                        }
//                    repository.getToken.first().token
//                        .also {
//                            Log.d("main", "intercept: $it")
//                        }
//                }
                appPreferenceProvider.getUserIdToken().also {
                    Log.d("main", "intercept: $it")
                }
            }"
        )
            .build()
        return chain.proceed(request)
    }


}
