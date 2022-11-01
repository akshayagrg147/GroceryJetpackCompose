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
import com.grocery.groceryapp.data.modal.*
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartItemsViewModal @Inject constructor(val dao: Dao, val repository: CommonRepository) :
    ViewModel() {
    private val allcartitems: MutableState<List<CartItems>> = mutableStateOf(emptyList())
    val allcartitemsState: State<List<CartItems>> = allcartitems

    private val itemcollections: MutableState<ItemsCollectionsResponse> =
        mutableStateOf(ItemsCollectionsResponse(null, null, null))
    val itemcollections1: State<ItemsCollectionsResponse> = itemcollections

    private val totalcount: MutableState<Int> =
        mutableStateOf(0)
    val totalcountState: State<Int> = totalcount
    private val totalPrice: MutableState<Int> =
        mutableStateOf(0)
    val totalPriceState: State<Int> = totalPrice



    private val orderConfirmedStatus: MutableState<OrderIdResponse> =
        mutableStateOf(OrderIdResponse())
    val orderConfirmedStatusState: State<OrderIdResponse> = orderConfirmedStatus

    private val CartCountPrice: FetchCart = FetchCart()


    private var productIdCountMutable: Int = 0


    private val addresslist: MutableState<List<AddressItems>> = mutableStateOf(emptyList())
    val addresslistState: State<List<AddressItems>> = addresslist


    fun deleteCartItems(productIdNumber: String?) = viewModelScope.launch(Dispatchers.IO) {

        var intger: Int =
            dao.getProductBasedIdCount(productIdNumber)
        intger -= 1

        if (intger >= 1) {
            dao
                .updateCartItem(intger, productIdNumber!!)
        } else if (intger == 0) {
            dao
                .deleteCartItem(productIdNumber)

        }
    }

    fun getItemBaseOnProductId(value: String?): String {
        viewModelScope.launch(Dispatchers.IO) {
            val intger: Int = dao.getProductBasedIdCount(value)
            productIdCountMutable = if (intger == 0) {
                1
            } else
                intger
        }
        return productIdCountMutable.toString()

    }

    suspend fun getAddress() = withContext(Dispatchers.IO) {
        dao.getAllAddress().collectLatest {
            addresslist.value = it
        }

    }

    fun insertCartItem(
        productIdNumber: String,
        thumb: String,
        price: Int,
        productname: String,
        actualprice: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        val intger: Int = dao.getProductBasedIdCount(productIdNumber)
        if (intger == 0) {
            dao
                .insertCartItem(
                    CartItems(
                        productIdNumber,
                        thumb, intger + 1,
                        price, productname, actualprice
                    )

                )

        } else if (intger >= 1) {
            dao.updateCartItem(intger + 1, productIdNumber)

        }


    }


    fun getCartPrice() {
        viewModelScope.launch() {

            var totalcount1: Int = 0
            var totalPrice1: Int = 0
            withContext(Dispatchers.IO) {
                totalcount1 = dao.getTotalProductItems().first()
                totalPrice1 = dao.getTotalProductItemsPrice().first()
            }
            Log.d("dnfvnvn",totalPrice.toString())
            totalcount.value = totalcount1
            totalPrice.value=totalPrice1

        }


    }


    suspend fun getcartItems() = withContext(Dispatchers.IO) {
        dao.getAllCartItems().collectLatest {
            allcartitems.value = it
        }

    }

    fun DeleteProduct(productIdNumber: String?) = viewModelScope.launch(Dispatchers.IO) {
        dao.deleteCartItem(productIdNumber)
    }

    fun calllingItemsCollectionsId(productIdIdModal: ProductIdIdModal) = viewModelScope.launch {
        Log.d("passingmessage", "calllingBestProductById: $productIdIdModal")
        repository.ItemsCollections(productIdIdModal).collectLatest {
            when (it) {
                is ApiState.Success -> {
                    itemcollections.value = it.data

                }
                is ApiState.Failure -> {
                    itemcollections.value = ItemsCollectionsResponse(null, it.msg.message, 401)

                }
                is ApiState.Loading -> {

                }

            }
        }

    }

    fun calllingBookingOrder(productIdIdModal: OrderIdCreateRequest) = viewModelScope.launch {
        repository.OrderIdRequest(productIdIdModal).collectLatest {
            when (it) {
                is ApiState.Success -> {
                    orderConfirmedStatus.value = it.data


                }
                is ApiState.Failure -> {
                    orderConfirmedStatus.value =
                        OrderIdResponse(message = it.msg.message, statusCode = 401)


                }
                is ApiState.Loading -> {

                }

            }
        }

    }


}