package com.grocery.groceryapp.features.Spash.domain.repository

import android.app.Activity
import com.google.android.gms.common.api.Api
import com.grocery.groceryapp.common.ApiState
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