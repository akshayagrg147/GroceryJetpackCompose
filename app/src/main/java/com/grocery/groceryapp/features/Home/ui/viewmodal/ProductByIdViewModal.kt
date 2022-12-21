package com.grocery.groceryapp.features.Home.ui.viewmodal

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.groceryapp.RoomDatabase.CartItems
import com.grocery.groceryapp.RoomDatabase.Dao
import com.grocery.groceryapp.RoomDatabase.RoomRepository
import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.common.doOnFailure
import com.grocery.groceryapp.common.doOnLoading
import com.grocery.groceryapp.common.doOnSuccess
import com.grocery.groceryapp.data.modal.HomeAllProductsResponse
import com.grocery.groceryapp.data.modal.ProductByIdResponseModal
import com.grocery.groceryapp.data.modal.ProductIdIdModal
import com.grocery.groceryapp.data.modal.RelatedSearchRequest
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductByIdViewModal @Inject constructor(
    val repository: CommonRepository,
    val dao: Dao,
    val repo: RoomRepository
) : ViewModel() {


    private val live: MutableState<ProductByIdResponseModal> =
        mutableStateOf(ProductByIdResponseModal(null, null, null))
    val responseLiveData: MutableState<ProductByIdResponseModal> = live

    private val relatedsearch: MutableState<HomeAllProductsResponse> =
        mutableStateOf(HomeAllProductsResponse(null, null, null))
    val relatedsearch1: MutableState<HomeAllProductsResponse> = relatedsearch


    private val totalcount: MutableState<Int> =
        mutableStateOf(0)
    val totalCountState: State<Int> = totalcount

    private val totalPrice: MutableState<Int> =
        mutableStateOf(0)
    val totalPriceState: State<Int> = totalPrice

    private val getItemCount: MutableState<Int> = mutableStateOf(0)
    val productIdCount: MutableState<Int> = getItemCount

    val _res: MutableSharedFlow<ProductIdIdModal> = MutableSharedFlow(1)

    fun setData(data: ProductIdIdModal) {
        _res.tryEmit(data)
    }

    fun deleteCartItems(value: ProductByIdResponseModal) = viewModelScope.launch(Dispatchers.IO) {

        repo.deleteCartItems(value.homeproducts?.productId!!)
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
                Integer.parseInt(value.homeproducts.price ?: "0"),
                value.homeproducts.productName!!,
                value.homeproducts.orignalprice,
                savingAmount = (value.homeproducts.orignalprice?.toInt()!! - value.homeproducts.price?.toInt()!!).toString()
            )
            repo.insert(data)
        } else if (intger >= 1) {
            repo.updateCartItem(intger + 1, value.homeproducts.productId)

        }
        getItemBaseOnProductId(value.homeproducts.productId)


    }

    fun getTotalProductItemsPrice() = viewModelScope.launch {
        repo.getTotalProductItemsPrice().catch { e -> Log.d("main", "Exception: ${e.message} ") }
            .collect {
                totalPrice.value = it ?: 0

            }
    }

    fun getTotalProductItems() = viewModelScope.launch {
        repo.getTotalProductItems().catch { e -> Log.d("main", "Exception: ${e.message} ") }
            .collect {
                totalcount.value = it ?: 0

            }

    }

    fun calllingAllProductById() = viewModelScope.launch {
        repository.callPendingProductById(_res.first()).collectLatest {
            when (it) {
                is ApiState.Success -> {
                    live.value = it.data

                }
                is ApiState.Failure -> {
                    live.value = ProductByIdResponseModal(null, it.msg.message, 401)

                }
                is ApiState.Loading -> {

                }

            }
        }

    }

    fun calllingExclsuiveProductById() = viewModelScope.launch {
        repository.callEclusiveById(_res.first()).collectLatest {
            when (it) {
                is ApiState.Success -> {
                    live.value = it.data

                }
                is ApiState.Failure -> {
                    live.value = ProductByIdResponseModal(null, it.msg.message, 401)

                }
                is ApiState.Loading -> {

                }

            }
        }

    }

    fun calllingBestProductById() = viewModelScope.launch {
        repository.callBestProductById(_res.first()).collectLatest {
            when (it) {
                is ApiState.Success -> {
                    live.value = it.data

                }
                is ApiState.Failure -> {
                    live.value = ProductByIdResponseModal(null, it.msg.message, 401)

                }
                is ApiState.Loading -> {

                }

            }
        }

    }

    fun calllingRelatedSearch() = viewModelScope.launch {
        repository.GetRelatedSearch(RelatedSearchRequest(Price = "50", category = "grocery"))
            .collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        relatedsearch.value = it.data

                    }
                    is ApiState.Failure -> {
                        Log.d("responsemessage", it.msg.toString())
                        relatedsearch.value = HomeAllProductsResponse(null, it.msg.message, 401)

                    }
                    is ApiState.Loading -> {

                    }

                }
            }

    }

    private val _eventRelatedSearchFlow = MutableSharedFlow<ApiState<HomeAllProductsResponse>>()
    val eventRelatedSearchFlow = _eventRelatedSearchFlow.asSharedFlow()

    fun onEvents(events: ProductEvents) {
        when (events) {
            is ProductEvents.RelatedSearchEvents -> {
                viewModelScope.launch {
                    repository.GetRelatedSearch(events.data)
                        .doOnSuccess {
                            _eventRelatedSearchFlow.emit(ApiState.Success(it))
                        }
                        .doOnFailure {
                            _eventRelatedSearchFlow.emit(ApiState.Failure(Throwable(it?.message)))
                        }
                        .doOnLoading {
                            _eventRelatedSearchFlow.emit(ApiState.Loading)
                        }
                        .collect()

                }
            }
        }
    }

}

sealed class ProductEvents {

    data class RelatedSearchEvents(val data: RelatedSearchRequest) : ProductEvents()

}