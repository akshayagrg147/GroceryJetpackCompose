package com.grocery.mandixpress.features.Home.ui.viewmodal

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.grocery.mandixpress.RoomDatabase.CartItems
import com.grocery.mandixpress.RoomDatabase.Dao
import com.grocery.mandixpress.RoomDatabase.RoomRepository
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.common.doOnFailure
import com.grocery.mandixpress.common.doOnLoading
import com.grocery.mandixpress.common.doOnSuccess
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.data.modal.ProductByIdResponseModal
import com.grocery.mandixpress.data.modal.ProductIdIdModal
import com.grocery.mandixpress.data.modal.RelatedSearchRequest
import com.grocery.mandixpress.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductByIdViewModal @Inject constructor(
    val sharedpreferenceCommon: sharedpreferenceCommon,
    val repository: CommonRepository,
    val dao: Dao,
    val repo: RoomRepository
) : ViewModel() {
//count total cart count
    private val totalcount: MutableState<Int> =
        mutableStateOf(0)
    val totalCountState: State<Int> = totalcount

    private val totalPrice: MutableState<Int> =
        mutableStateOf(0)
    val totalPriceState: State<Int> = totalPrice
//count in between + and -
    private val getItemCount: MutableState<Int> = mutableStateOf(0)
    val productIdCount: MutableState<Int> = getItemCount

    private val _eventRelatedSearchFlow:MutableStateFlow<ComposeUiResponse<HomeAllProductsResponse>> = MutableStateFlow(
        ComposeUiResponse()
    )
    var eventRelatedSearchFlow = _eventRelatedSearchFlow.asStateFlow()
        private set

    private val _itemDetailFlow:MutableStateFlow<ComposeUiResponse<ProductByIdResponseModal>> = MutableStateFlow(
        ComposeUiResponse()
    )
    var itemDetailFlow = _itemDetailFlow.asStateFlow()
        private set

    fun deleteCartItems(value: ProductByIdResponseModal) = viewModelScope.launch(Dispatchers.IO) {

        repo.deleteCartItems(value. homeproducts?.productId!!)
        getItemBaseOnProductId(value.homeproducts.productId)
    }

    fun getItemBaseOnProductId(value: String) = viewModelScope.launch(Dispatchers.IO) {
        val intger: Int = repo.getProductBasedIdCount(value).first() ?: 0
        getItemCount.value = intger
    }

    fun insertCartItem(value: ProductByIdResponseModal) = viewModelScope.launch(Dispatchers.IO) {
        val intger: Int = repo.getProductBasedIdCount(value.homeproducts?.productId!!).first() ?: 0
        if (intger == 0) {
            val data = CartItems(
                value.homeproducts.productId,
                value.homeproducts.productImage1,
                intger + 1,
                Integer.parseInt(value.homeproducts.orignalprice ?: "0"),
                value.homeproducts.productName!!,
                value.homeproducts.orignalprice,
                savingAmount = (value.homeproducts.orignalprice?.toInt()!! - value.homeproducts.selling_price?.toInt()!!).toString()
            )
            repo.insert(data)
        } else if (intger >= 1) {
            repo.updateCartItem(intger + 1, value.homeproducts.productId)

        }
        getItemBaseOnProductId(value.homeproducts.productId)


    }
//cart items price
    fun getTotalProductItemsPrice() = viewModelScope.launch {
        repo.getTotalProductItemsPrice().catch { e ->
            Log.d("main", "Exception: ${e.message} ") }
            .collect {
                totalPrice.value = it ?: 0

            }
    }
//cart items count
    fun getTotalProductItems() = viewModelScope.launch {
        repo.getTotalProductItems().catch { e ->
            Log.d("main", "Exception: ${e.message} ") }
            .collectLatest {
                totalcount.value = it ?: 0

            }

    }


    fun onEvents(events: ProductEvents) {
        when (events) {
            is ProductEvents.RelatedSearchEvents -> {
                viewModelScope.launch {
                    events.data.pincode=sharedpreferenceCommon.getPostalCode()
                    repository.GetRelatedSearch(events.data)
                        .collectLatest {
                            when(it){


                                is ApiState.Success->{
                                    _eventRelatedSearchFlow.value = ComposeUiResponse(data = it.data)


                                }
                                is ApiState.Failure->{
                                    _eventRelatedSearchFlow.value = ComposeUiResponse( error = it?.msg.toString())
                                }
                                is ApiState.Loading->{
                                    _eventRelatedSearchFlow.value= ComposeUiResponse( isLoading = true)
                                }
                            }


                        }


                }
            }
            is ProductEvents.ItemDetailEvent->{
                viewModelScope.launch {
                    repository.callBestProductById(events.data)
                        .doOnSuccess {

                            _itemDetailFlow.value = ComposeUiResponse(data = it)
                        }
                        .doOnFailure {
                            _itemDetailFlow.value = ComposeUiResponse( error = it?.message.toString())
                        }
                        .doOnLoading {

                            _itemDetailFlow.value= ComposeUiResponse( isLoading = true)

                        }
                        .collect()

                }

            }

        }
    }
    fun getFreeDeliveryMinPrice():String{
        return sharedpreferenceCommon.getMinimumDeliveryAmount()
    }

}

sealed class ProductEvents {

    data class RelatedSearchEvents(val data: RelatedSearchRequest) : ProductEvents()
    data class ItemDetailEvent(val data: ProductIdIdModal) : ProductEvents()



}