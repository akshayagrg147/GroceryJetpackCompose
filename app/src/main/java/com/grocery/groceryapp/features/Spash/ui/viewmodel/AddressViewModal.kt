package com.grocery.groceryapp.features.Spash.ui.viewmodel

import android.location.Address
import android.util.Log
import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewModelScope
import com.grocery.groceryapp.RoomDatabase.CartItems
import com.grocery.groceryapp.RoomDatabase.Dao
import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.data.modal.ProductByIdResponseModal
import com.grocery.groceryapp.data.modal.ProductIdIdModal
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddressViewModal @Inject constructor(val dao: Dao):ViewModel() {
    private val live:MutableState<List<CartItems>> = mutableStateOf(emptyList())
    val responseLiveData:MutableState<List<CartItems>> =live

    fun saveAddress(address: AddressItems)=viewModelScope.launch(Dispatchers.IO){
        dao.insertAddressItem(address)

    }
    fun UpdateAddress(address: AddressItems)=viewModelScope.launch(Dispatchers.IO){
        dao.updateAddressItem(
            address.customer_name,address.customer_PhoneNumber,address.PinCode,address.Address1,address.Address2,address.LandMark,address.id)


    }






}