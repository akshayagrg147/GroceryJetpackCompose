package com.grocery.groceryapp.features.Spash.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.grocery.groceryapp.RoomDatabase.CartItems
import com.grocery.groceryapp.RoomDatabase.Dao

import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.data.modal.*
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartItemsViewModal @Inject constructor( val dao: Dao,val repository: CommonRepository):ViewModel() {
    private val live:MutableState<List<CartItems>> = mutableStateOf(emptyList())
    val responseLiveData:State<List<CartItems>> =live

    private val itemcollections:MutableState<ItemsCollectionsResponse> = mutableStateOf(ItemsCollectionsResponse(null,null,null))
    val itemcollections1:State<ItemsCollectionsResponse> =itemcollections

    private val bookedorderResponse:MutableState<OrderIdResponse> = mutableStateOf(OrderIdResponse())
    val bookedorderResponse1:State<OrderIdResponse> =bookedorderResponse

    private val updatecount:MutableState<FetchCart> =mutableStateOf(FetchCart())
    val getitemcount:State<FetchCart> =updatecount

    private var productIdCountMutable:Int =0
    var productIdCount:Int =productIdCountMutable

    private val addresslist:MutableState<List<AddressItems>> = mutableStateOf(emptyList())
    val list:State<List<AddressItems>> =addresslist



    fun setcartvalue(value: Int){

        productIdCountMutable=value
    }
    fun getcartvalue():Int{
       return productIdCount
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

    fun getItemBaseOnProductId(value: String?):String{
        viewModelScope.launch(Dispatchers.IO){
            Log.d("callingcart", "getItemBaseOnProductId: --")
            val intger: Int = dao.getProductBasedIdCount(value)
            Log.d("callingcart", "getItemBaseOnProductId: $intger")
            if (intger == 0) {
                productIdCountMutable = 1
            }    else
                productIdCountMutable=intger
        }
        return productIdCountMutable.toString()

    }
    suspend fun getAddress()= withContext(Dispatchers.IO) {
       dao.getAllAddress().collectLatest {
           addresslist.value=it
       }

    }
    fun insertCartItem(value: CartItems)=viewModelScope.launch (Dispatchers.IO){
        val intger: Int = dao.getProductBasedIdCount(value.ProductIdNumber!!)
        if (intger == 0) {
            dao
                .insertCartItem(
                    CartItems(
                        value.ProductIdNumber,
                        value.strCategoryThumb,intger + 1,
                        value.strProductPrice, value.strProductName,value.actualprice)

                )

        } else if (intger >= 1) {
            dao.updateCartItem(intger + 1,  value.ProductIdNumber)

        }



    }
    suspend fun getCartPrice()= withContext(Dispatchers.IO){
        var totalcount: Int =
            dao.getTotalProductItems().first()
        var totalPrice: Int =
            dao.getTotalProductItemsPrice().first()
        Log.d("updatingdata", totalPrice.toString())
        updatecount.value.totalcount=totalcount
        updatecount.value.totalprice=totalPrice



    }


   suspend fun getcartItems()= withContext(Dispatchers.IO){
        dao.getAllCartItems().collectLatest {
            live.value=it
        }

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
    fun calllingBookingOrder(productIdIdModal: OrderIdCreateRequest)=viewModelScope.launch {
        repository.OrderIdRequest(productIdIdModal).collectLatest {
            when(it){
                is ApiState.Success->{
                    bookedorderResponse.value=it.data



                }
                is ApiState.Failure->{
                    bookedorderResponse.value= OrderIdResponse(message = it.msg.message, statusCode = 401)


                }
                is ApiState.Loading->{

                }

            }
        }

    }


}