package com.grocery.mandixpress.features.splash.ui.viewmodel

import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class MapScreenViewModal @Inject constructor(val sharedpreference: sharedpreferenceCommon) : ViewModel() {

    fun ConvertLatLngToAddress(latitude: Double, longitude: Double, gcd: Geocoder,address:(List<Address>)->Unit) {
        viewModelScope.launch {
            try {
                var addresses: List<Address>
                val deferred = this.async(Dispatchers.IO) {
                    addresses = gcd.getFromLocation(
                        latitude,
                        longitude,
                        1
                    )?: emptyList() // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                    return@async addresses
                }
                withContext(Dispatchers.Main) {
                    val data = deferred.await()
                    address(data)

                }

            } catch (e: Exception) {
                Log.e("location", e.message!!)
            }
        }

    }
    fun setAddress(address: String, city: String){
        sharedpreference.setCombineAddress(address)
        sharedpreference.setCity(city)

    }

    fun savePinCode(extractSixDigitNumber: String?) {
        sharedpreference.setPinCode(extractSixDigitNumber?:"")

    }
}