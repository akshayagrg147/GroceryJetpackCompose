package com.grocery.mandixpress.features.splash.domain.repository

import android.app.Activity
import com.grocery.mandixpress.common.ApiState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    fun createUserWithPhone(
        mobile:String,
        activity: Activity
    ) :Flow<ApiState<String>>

    fun signInWithCredentials(
        code:String
    ) : Flow<ApiState<String>>

}