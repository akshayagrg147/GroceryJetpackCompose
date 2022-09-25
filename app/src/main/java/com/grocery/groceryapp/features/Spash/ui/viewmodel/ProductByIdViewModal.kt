package com.grocery.groceryapp.features.Spash.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.data.modal.ProductByIdResponseModal
import com.grocery.groceryapp.data.modal.ProductIdIdModal
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductByIdViewModal @Inject constructor(val repository: CommonRepository):ViewModel() {
    private val live:MutableState<ProductByIdResponseModal> = mutableStateOf(ProductByIdResponseModal(null,null,null))
    val responseLiveData:MutableState<ProductByIdResponseModal> =live


    fun calllingAllProductById(productIdIdModal: ProductIdIdModal)=viewModelScope.launch {
        Log.d("passingmessage", "calllingAllProductById: $productIdIdModal")
        repository.callPendingProductById(productIdIdModal).collectLatest {
            when(it){
               is ApiState.Success->{
                   live.value=it.data

                }
               is ApiState.Failure->{
                   live.value= ProductByIdResponseModal(null,it.msg.message,401)

                }
                is ApiState.Loading->{

                }

            }
        }

    }
    fun calllingExclsuiveProductById(productIdIdModal: ProductIdIdModal)=viewModelScope.launch {
        Log.d("passingmessage", "calllingExProductById: $productIdIdModal")
        repository.callEclusiveById(productIdIdModal).collectLatest {
            when(it){
                is ApiState.Success->{
                    live.value=it.data

                }
                is ApiState.Failure->{
                    live.value= ProductByIdResponseModal(null,it.msg.message,401)

                }
                is ApiState.Loading->{

                }

            }
        }

    }
    fun calllingBestProductById(productIdIdModal: ProductIdIdModal)=viewModelScope.launch {
        Log.d("passingmessage", "calllingBestProductById: $productIdIdModal")
        repository.callBestProductById(productIdIdModal).collectLatest {
            when(it){
                is ApiState.Success->{
                    live.value=it.data

                }
                is ApiState.Failure->{
                    live.value= ProductByIdResponseModal(null,it.msg.message,401)

                }
                is ApiState.Loading->{

                }

            }
        }

    }
}