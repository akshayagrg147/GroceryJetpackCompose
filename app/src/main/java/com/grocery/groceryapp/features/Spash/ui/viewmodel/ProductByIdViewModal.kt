package com.grocery.groceryapp.features.Spash.ui.viewmodel

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
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductByIdViewModal @Inject constructor(val repository: CommonRepository,val dao: Dao):ViewModel() {
    private val live:MutableState<ProductByIdResponseModal> = mutableStateOf(ProductByIdResponseModal(null,null,null))
    val responseLiveData:MutableState<ProductByIdResponseModal> =live

    private val updatecount:MutableState<Int> =mutableStateOf(0)
    val getitemcount:MutableState<Int> =updatecount

    private val getItemCount:MutableState<Int> =mutableStateOf(0)
    val productIdCount:MutableState<Int> =getItemCount

 fun deleteCartItems(value: ProductByIdResponseModal)=viewModelScope.launch(Dispatchers.IO){

     var intger: Int =
         dao.getProductBasedIdCount(value.homeproducts?.productId!!)
     intger -= 1

     if (intger >= 1) {
         dao
             .updateCartItem(intger, value.homeproducts.productId!!)
     } else if (intger == 0) {
         dao
             .deleteCartItem(value.homeproducts.productId)

     }
}

    fun getItemBaseOnProductId(value: ProductByIdResponseModal)=viewModelScope.launch(Dispatchers.IO){
        val intger: Int = dao.getProductBasedIdCount(value.homeproducts?.productId!!)
        getItemCount.value=intger
    }
    fun insertCartItem(value: ProductByIdResponseModal)=viewModelScope.launch (Dispatchers.IO){
       val intger: Int = dao.getProductBasedIdCount(value.homeproducts?.productId!!)
       Log.d("jdjjdjd",intger.toString())
       if (intger == 0) {
           dao
               .insertCartItem(
                   CartItems(
                       value.homeproducts.productId,
                       value.homeproducts.productImage1,intger + 1,
                       Integer.parseInt(value.homeproducts.price?:"1000"), value.homeproducts.productName!!)

               )

       } else if (intger >= 1) {
           dao.updateCartItem(intger + 1,  value.homeproducts.productId)
           val intger1: Int = dao.getProductBasedIdCount(value.homeproducts?.productId!!)
           Log.d("jdjjdjd",intger1.toString())
       }



    }
    fun getCartItem()= viewModelScope.launch(Dispatchers.IO){
        var totalcount: Int =
            dao.getTotalProductItems()
        updatecount.value=totalcount


    }
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