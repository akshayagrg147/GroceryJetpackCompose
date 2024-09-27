package com.grocery.mandixpress.features.splash.ui.viewmodel

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.grocery.mandixpress.BuildConfig
import com.grocery.mandixpress.SharedPreference.CombinedSharedPreference
import com.grocery.mandixpress.features.splash.domain.repository.CommonRepository
import com.grocery.mandixpress.data.modal.RegisterLoginRequest
import com.grocery.mandixpress.data.modal.RegisterLoginResponse
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.data.modal.SocietyListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegisterLoginViewModal @Inject constructor(private val repository: CommonRepository, private val sharedpreference: CombinedSharedPreference, @ApplicationContext applicationContext: Context) : ViewModel() {

    init {
        Places.initialize(applicationContext, BuildConfig.api_key)
    }

    val field = listOf(Place.Field.NAME, Place.Field.LAT_LNG)
    private val _registerEventFlow:MutableSharedFlow<ApiState<RegisterLoginResponse>> = MutableSharedFlow()
    private val _societyEventFlow:MutableSharedFlow<ApiState<SocietyListResponse>> = MutableSharedFlow()

    var societyEventFlow = _societyEventFlow.asSharedFlow()
    private set
    var registerEventFlow = _registerEventFlow.asSharedFlow()
    private set

    fun onEvent(event: RegisterEvent){
        when(event){
            is RegisterEvent.RegisterEventFlow -> viewModelScope.launch {
                repository.registerUser(event.data)
                    .onStart {
                        _registerEventFlow.emit(ApiState.Loading)
                    }
                    .catch {
                        _registerEventFlow.emit(ApiState.Failure(it))
                    }
                    .collect{
                        _registerEventFlow.emit(it)
                    }
            }
            is RegisterEvent.AllSocietyEventFlow -> viewModelScope.launch {
                repository.getAllSociety()
                    .onStart {
                        _societyEventFlow.emit(ApiState.Loading)
                    }
                    .catch {
                        _societyEventFlow.emit(ApiState.Failure(it))
                    }
                    .collect{
                        _societyEventFlow.emit(it)
                    }
            }
        }
    }
    fun ConvertLatLngToAddress(latitude: Double, longitude: Double, gcd: Geocoder, address:(List<Address>)->Unit) {
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
                Log.e("location", e.message?:"something went wrong")
            }
        }

    }
    fun setAddress(address: String, city: String){
        sharedpreference.setCombineAddress(address)
        sharedpreference.setCity(city)

    }
    fun saveLatLng(latitude: String, longitude: String){
        sharedpreference.setLngLng(latitude,longitude)
    }

    fun savePinCode(extractSixDigitNumber: String?) {
        sharedpreference.setPinCode(extractSixDigitNumber?:"")

    }

    fun getJwtToken():String {
        return sharedpreference.getJwtToken()

    }

    fun getCombinedAddress(): String {
        return sharedpreference.getCombinedAddress()

    }

    fun setMobileNumber(mobileNumber: String) {
        sharedpreference.setMobileNumber(mobileNumber)

    }

    fun setJwtToken(token: String) {
        sharedpreference.setJwtToken(token)

    }

    fun setCombineAddress(add: String) {
        sharedpreference.setCombineAddress(add)

    }


}

sealed class RegisterEvent{
    data class RegisterEventFlow(val data:RegisterLoginRequest) : RegisterEvent()
    object AllSocietyEventFlow : RegisterEvent()

}
