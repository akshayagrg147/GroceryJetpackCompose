package com.grocery.mandixpress.features.home.ui.viewmodal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.grocery.mandixpress.roomdatabase.Dao
import com.grocery.mandixpress.sharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.features.home.domain.modal.AddressItems
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressViewModal @Inject constructor(
    val dao: Dao,
    val sharedpreferenceCommon: sharedpreferenceCommon
) : ViewModel() {
    val predictions: MutableStateFlow<List<AutocompletePrediction>> = MutableStateFlow(emptyList())

    fun getAllAvailablePostalCodes(): List<PinCodeStateModal> {
        return sharedpreferenceCommon.getAvailablePinCode()
    }

    fun searchAddress(
        query: String,
        placesClient: PlacesClient
    ): Flow<List<AutocompletePrediction>> {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()
        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            viewModelScope.launch {
                predictions.emit(response.autocompletePredictions)
            }
        }
        return predictions
    }

    fun saveAddress(address: AddressItems) = viewModelScope.launch(Dispatchers.IO) {
        dao.insertAddressItem(address)
    }

    fun UpdateAddress(address: AddressItems) = viewModelScope.launch(Dispatchers.IO) {
        dao.updateAddressItem(
            address.customer_name?:"",
            address.customer_phoneNumber?:"",
            address.pinCode?:0,
            address.address1?:"",
            address.address2?:"",
            address.landMark?:"",
            address.id
        )

    }


}