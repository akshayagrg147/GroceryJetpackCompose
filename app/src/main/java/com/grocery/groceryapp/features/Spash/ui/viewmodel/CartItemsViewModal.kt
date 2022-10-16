package com.grocery.groceryapp.features.Spash.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.groceryapp.RoomDatabase.CartItems
import com.grocery.groceryapp.RoomDatabase.Dao
import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.data.modal.ItemsCollectionsResponse
import com.grocery.groceryapp.data.modal.ProductIdIdModal
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartItemsViewModal @Inject constructor( val dao: Dao,val repository: CommonRepository):ViewModel() {
    private val live:MutableState<List<CartItems>> = mutableStateOf(emptyList())
    val responseLiveData:MutableState<List<CartItems>> =live

    private val itemcollections:MutableState<ItemsCollectionsResponse> = mutableStateOf(ItemsCollectionsResponse(null,null,null))
    val itemcollections1:MutableState<ItemsCollectionsResponse> =itemcollections



    private val updatecount:MutableState<Int> =mutableStateOf(0)
    val getitemcount:MutableState<Int> =updatecount

    private val productIdCountMutable:MutableState<Int> =mutableStateOf(0)
    val productIdCount:MutableState<Int> =productIdCountMutable

    private val addresslist:MutableState<List<AddressItems>> = mutableStateOf(emptyList())
    val list:MutableState<List<AddressItems>> =addresslist

    fun getItemsCollections(){

    }

    fun deleteCartItems(value: CartItems)=viewModelScope.launch(Dispatchers.IO){

        var intger: Int =
            dao.getProductBasedIdCount(value.ProductIdNumber!!)
        intger -= 1

        if (intger >= 1) {
            dao
                .updateCartItem(intger, value.ProductIdNumber!!)
        } else if (intger == 0) {
            dao
                .deleteCartItem(value.ProductIdNumber)

        }
    }

    fun getItemBaseOnProductId(value: String?)=viewModelScope.launch(Dispatchers.IO){
        Log.d("callingcart", "getItemBaseOnProductId: --")
        val intger: Int = dao.getProductBasedIdCount(value)
        Log.d("callingcart", "getItemBaseOnProductId: $intger")
        if (intger == 0) {
            productIdCountMutable.value = 1
        }    else
            productIdCountMutable.value=intger
    }
    fun getAddress()=viewModelScope.launch(Dispatchers.IO) {
        list.value= dao.getAllAddress()
    }
    fun insertCartItem(value: CartItems)=viewModelScope.launch (Dispatchers.IO){
        val intger: Int = dao.getProductBasedIdCount(value.ProductIdNumber!!)
        if (intger == 0) {
            dao
                .insertCartItem(
                    CartItems(
                        value.ProductIdNumber,
                        value.strCategoryThumb,intger + 1,
                        value.strProductPrice, value.strProductName)

                )

        } else if (intger >= 1) {
            dao.updateCartItem(intger + 1,  value.ProductIdNumber)

        }



    }
    fun getCartItem()= viewModelScope.launch(Dispatchers.IO){
        var totalcount: Int =
            dao.getTotalProductItems()
        updatecount.value=totalcount


    }


    fun getcartItems()=viewModelScope.launch(Dispatchers.IO){
        live.value=dao.getAllCartItems()

    }
    fun DeleteProduct(productIdNumber: String?) =viewModelScope.launch(Dispatchers.IO){
        dao.deleteCartItem(productIdNumber)
    }

    fun calllingItemsCollectionsId(productIdIdModal: ProductIdIdModal)=viewModelScope.launch {
        Log.d("passingmessage", "calllingBestProductById: $productIdIdModal")
        repository.ItemsCollections(productIdIdModal).collectLatest {
            when(it){
                is ApiState.Success->{
                    itemcollections.value=it.data

                }
                is ApiState.Failure->{
                    itemcollections.value= ItemsCollectionsResponse(null,it.msg.message,401)

                }
                is ApiState.Loading->{

                }

            }
        }

    }


}