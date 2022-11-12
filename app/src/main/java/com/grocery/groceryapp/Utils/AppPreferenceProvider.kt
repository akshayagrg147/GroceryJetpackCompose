package com.grocery.groceryapp.Utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

private const val USER_ID_TOKEN = "USER_ID_TOKEN"
private const val AWS_USER_ID = "AWS_USER_ID"
private const val AWS_USER_NAME = "AWS_USER_NAME"
private const val IS_USER_LOGGED_IN = "IS_USER_LOGGED_IN"
private const val IS_TNC_ACCEPTED = "IS_TNC_ACCEPTED"


class AppPreferenceProvider @Inject constructor(@ApplicationContext private val appContext: Context) {

    private val preferences: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun setUserIdToken(value: String?) {
        preferences.edit().putString(USER_ID_TOKEN, value).apply()
    }

    fun getUserIdToken(): String {
        return preferences.getString(USER_ID_TOKEN, "")!!
    }

    fun setAwsUserId(value: String) {
        preferences.edit().putString(AWS_USER_ID, value).apply()

    }

    fun getAwsUserId(): String {
        return preferences.getString(AWS_USER_ID, "")!!
    }

    fun setAwsUserName(value: String) {
        preferences.edit().putString(AWS_USER_NAME, value).apply()

    }

    fun getAwsUserName(): String {
        return preferences.getString(AWS_USER_NAME, "")!!
    }


    fun setIsUserLoggedIn(value: String) {
        preferences.edit().putString(IS_USER_LOGGED_IN, value).apply()

    }

    fun getIsUserLoggedIn(): String {
        return preferences.getString(IS_USER_LOGGED_IN, "")!!
    }


    fun setTNCAccepted(value: String) {
        preferences.edit().putString(IS_TNC_ACCEPTED, value).apply()

    }

    fun getTNCAccepted(): String {
        return preferences.getString(IS_TNC_ACCEPTED, "")!!
    }


}
