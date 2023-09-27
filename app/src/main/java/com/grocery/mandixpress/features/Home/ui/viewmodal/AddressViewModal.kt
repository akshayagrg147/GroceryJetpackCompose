package com.grocery.mandixpress.features.Home.ui.viewmodal

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.grocery.mandixpress.RoomDatabase.CartItems
import com.grocery.mandixpress.RoomDatabase.Dao
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.features.Home.domain.modal.AddressItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class   AddressViewModal @Inject constructor(val dao: Dao,val sharedpreferenceCommon: sharedpreferenceCommon):ViewModel() {
    private val live:MutableState<List<CartItems>> = mutableStateOf(emptyList())
    val responseLiveData:MutableState<List<CartItems>> =live
    val predictions: MutableStateFlow<List<AutocompletePrediction>> = MutableStateFlow(emptyList())

fun getAllAvailablePostalCodes():List<PinCodeStateModal>{
    return sharedpreferenceCommon.getAvailablePinCode()
}

    fun searchAddress(query: String,placesClient: PlacesClient): Flow<List<AutocompletePrediction>> {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()


            placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
                viewModelScope.launch {
                 predictions.emit(response.autocompletePredictions)
            }
        }
        return  predictions
    }

    fun saveAddress(address: AddressItems)=viewModelScope.launch(Dispatchers.IO){
        dao.insertAddressItem(address)
    }
    fun UpdateAddress(address: AddressItems)=viewModelScope.launch(Dispatchers.IO){
        dao.updateAddressItem(
            address.customer_name,address.customer_PhoneNumber,address.PinCode,address.Address1,address.Address2,address.LandMark,address.id)


    }






}