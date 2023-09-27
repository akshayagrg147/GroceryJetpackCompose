package com.grocery.mandixpress.SharedPreference

import android.content.Context
import android.content.SharedPreferences
import com.orders.resturantorder.SharedPreference.AppConstant
import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Inject

class sharedpreferenceCommon @Inject constructor(@ApplicationContext mContext: Context) {
    private var mPrefs: SharedPreferences =
        mContext.getSharedPreferences(AppConstant.PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun setCity(city: String): Any {
        return mPrefs.edit().putString(AppConstant.city, city).apply()
    }

    fun isFirstTime(first_time: Boolean): Any {
        return mPrefs.edit().putBoolean(AppConstant.first_time, first_time).apply()
    }

    fun getisFirstTime(): Boolean {
        return mPrefs.getBoolean(AppConstant.first_time, false)
    }

    fun setState(state: String): Any {
        return mPrefs.edit().putString(AppConstant.state, state).apply()
    }

    fun setPinCode(picode: String): Any {
        return mPrefs.edit().putString(AppConstant.pincode, picode).apply()
    }

    fun setCombineAddress(comibineAddress: String): Any {
        return mPrefs.edit().putString(AppConstant.combine, comibineAddress).apply()
    }

    fun setSearchAddress(address: String): Any {
        return mPrefs.edit().putString(AppConstant.serarchSelection, address).apply()
    }
    fun getSearchAddress(): String {
        return mPrefs.getString(AppConstant.serarchSelection, "").toString()
    }


    fun setLat(lat: String): Any {
        return mPrefs.edit().putString(AppConstant.lat, lat).apply()
    }

    fun setLng(lng: String): Any {
        return mPrefs.edit().putString(AppConstant.lng, lng).apply()
    }

    fun getCity(): String {
        return mPrefs.getString(AppConstant.city, "").toString()
    }

    fun getState(): String {
        return mPrefs.getString(AppConstant.state, null).toString()
    }

    fun getPostalCode(): String {
        return mPrefs.getString(AppConstant.pincode, "").toString()
    }

    fun getLat(): String {
        return mPrefs.getString(AppConstant.lat, "").toString()
    }

    fun getLng(): String {
        return mPrefs.getString(AppConstant.lng, "").toString()
    }

    fun getCombinedAddress(): String {
        return mPrefs.getString(AppConstant.combine, "null").toString()
    }

    fun getJwtToken(): String {
        return mPrefs.getString(AppConstant.jwttoken, "").toString()
    }
    fun getMobileNumber(): String {
        return mPrefs.getString(AppConstant.mobilenumber, "").toString()
    }

    fun setJwtToken(token: String): Any {
        return mPrefs.edit().putString(AppConstant.jwttoken, token).apply()
    }
    fun setMobileNumber(number: String){
        return mPrefs.edit().putString(AppConstant.mobilenumber, number).apply()
    }
    fun getImageUri():String{
        return mPrefs.getString(AppConstant.imageuri,"").toString()
    }
    fun setImageUri(image: String){
        return mPrefs.edit().putString(AppConstant.imageuri, image).apply()
    }

    fun clearSharePreference():Boolean{
        return mPrefs.edit().clear().commit()
    }

    fun setAvailablePinCode(pincode: String) {
        return mPrefs.edit().putString(AppConstant.availablePinCode, pincode).apply()

    }
    fun setMinimumDeliveryAmount(pincode: String) {
        return mPrefs.edit().putString(AppConstant.minimumAmountDelivery, pincode).apply()

    }
    fun getMinimumDeliveryAmount():String{
        return mPrefs.getString(AppConstant.minimumAmountDelivery,"").toString()
    }
    fun getAvailablePinCode():String{
        return mPrefs.getString(AppConstant.availablePinCode,"").toString()
    }


}