package com.grocery.groceryapp.features.Home.ui.viewmodal

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.groceryapp.RoomDatabase.CartItems
import com.grocery.groceryapp.RoomDatabase.Dao
import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.data.modal.*
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductByIdViewModal @Inject constructor(val repository: CommonRepository,val dao: Dao):ViewModel() {

    private val live:MutableState<ProductByIdResponseModal> = mutableStateOf(ProductByIdResponseModal(null,null,null))
    val responseLiveData:MutableState<ProductByIdResponseModal> =live

    private val relatedsearch:MutableState<HomeAllProductsResponse> = mutableStateOf(HomeAllProductsResponse(null,null,null))
    val relatedsearch1:MutableState<HomeAllProductsResponse> =relatedsearch

var valueCart:ProductByIdResponseModal=ProductByIdResponseModal(homeproducts = null, message = "", statusCode = 101)
    private val updatecount:MutableState<FetchCart> =mutableStateOf(FetchCart())
    val getitemcount:MutableState<FetchCart> =updatecount

    private val getItemCount:MutableState<Int> =mutableStateOf(0)
    val productIdCount:MutableState<Int> =getItemCount

    val _res: MutableSharedFlow<ProductIdIdModal> = MutableSharedFlow(1)

    fun setData(data: ProductIdIdModal){
        _res.tryEmit(data)
    }

 fun deleteCartItems(value: ProductByIdResponseModal)=viewModelScope.launch(Dispatchers.IO){

     var intger: Int =
         dao.getProductBasedIdCount(value.homeproducts?.productId!!).first()?:0
     intger -= 1

     if (intger >= 1) {
         dao
             .updateCartItem(intger, value.homeproducts.productId!!)
     } else if (intger == 0) {
         dao
             .deleteCartItem(value.homeproducts.productId)

     }
     getItemBaseOnProductId(value)
}

    fun getItemBaseOnProductId(value: ProductByIdResponseModal)=viewModelScope.launch(Dispatchers.IO){
        val intger: Int = dao.getProductBasedIdCount(value.homeproducts?.productId!!).first()?:0
        getItemCount.value=intger
    }
    fun insertCartItem(value: ProductByIdResponseModal)=viewModelScope.launch (Dispatchers.IO){
       val intger: Int = dao.getProductBasedIdCount(value.homeproducts?.productId!!).first()?:0
        valueCart=value
       if (intger == 0) {
           dao
               .insertCartItem(
                   CartItems(
                       value.homeproducts.productId,
                       value.homeproducts.productImage1,intger + 1,
                       Integer.parseInt(value.homeproducts.price?:"0"), value.homeproducts.productName!!,value.homeproducts.orignalprice,savingAmount = (value.homeproducts.orignalprice?.toInt()!!-value.homeproducts.price?.toInt()!!).toString())

               )

       } else if (intger >= 1) {
           dao.updateCartItem(intger + 1,  value.homeproducts.productId)
           val intger1: Int = dao.getProductBasedIdCount(value.homeproducts?.productId!!).first()?:0
       }
        getItemBaseOnProductId(value)




    }
    fun getCartItem()= viewModelScope.launch(Dispatchers.IO){
        if( dao.getTotalProductItems()!=null){
            var totalcount: Int =
                dao.getTotalProductItems()?.first()?:0
            var totalPrice: Int =
                dao.getTotalProductItemsPrice()?.first()?:0

            updatecount.value.totalcount=totalcount
            updatecount.value.totalprice=totalPrice
        }



    }
    fun calllingAllProductById()=viewModelScope.launch {
        repository.callPendingProductById(_res.first()).collectLatest {
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
    fun calllingExclsuiveProductById()=viewModelScope.launch {
        repository.callEclusiveById(_res.first()).collectLatest {
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
    fun calllingBestProductById()=viewModelScope.launch {
        repository.callBestProductById(_res.first()).collectLatest {
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
    fun calllingRelatedSearch()=viewModelScope.launch {
        repository.GetRelatedSearch(RelatedSearchRequest(Price = "50", category = "grocery")).collectLatest {
            when(it){
                is ApiState.Success->{
                    relatedsearch.value=it.data

                }
                is ApiState.Failure->{
                    Log.d("responsemessage",it.msg.toString())
                    relatedsearch.value= HomeAllProductsResponse(null,it.msg.message,401)

                }
                is ApiState.Loading->{

                }

            }
        }

    }




}