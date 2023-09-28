package com.grocery.mandixpress.features.Home.ui.viewmodal

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.mandixpress.RoomDatabase.CartItems
import com.grocery.mandixpress.RoomDatabase.Dao
import com.grocery.mandixpress.RoomDatabase.RoomRepository
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.common.doOnFailure
import com.grocery.mandixpress.common.doOnLoading
import com.grocery.mandixpress.common.doOnSuccess
import com.grocery.mandixpress.data.modal.ItemsCollectionsResponse
import com.grocery.mandixpress.data.modal.OrderIdCreateRequest
import com.grocery.mandixpress.data.modal.OrderIdResponse
import com.grocery.mandixpress.data.modal.ProductIdIdModal
import com.grocery.mandixpress.features.Home.domain.modal.AddressItems
import com.grocery.mandixpress.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartItemsViewModal @Inject constructor(val sharedpreferenceCommon: sharedpreferenceCommon,
    val dao: Dao,
    val repository: CommonRepository,
    val repo: RoomRepository
) :
    ViewModel() {
    private val emitProductId: MutableSharedFlow<ProductIdIdModal> = MutableSharedFlow(1)
    private val allCartItems: MutableState<List<CartItems>> = mutableStateOf(emptyList())
    val allCartItemsState: State<List<CartItems>> = allCartItems
    private val savingAmountMutable: MutableState<Int> = mutableStateOf(0)
    val savingAmountState: State<Int> = savingAmountMutable
    private val itemsCollection: MutableState<ItemsCollectionsResponse> = mutableStateOf(ItemsCollectionsResponse(null, null, null))
    val _itemsCollection: State<ItemsCollectionsResponse> = itemsCollection
    val _res: MutableSharedFlow<OrderIdCreateRequest> = MutableSharedFlow(1)
    private val totalCount: MutableState<Int> = mutableStateOf(0)
    val totalCountState: State<Int> = totalCount
    private val totalPrice: MutableState<Int> =
        mutableStateOf(0)
    val totalPriceState: State<Int> = totalPrice

    private val addresslist: MutableState<List<AddressItems>> = mutableStateOf(emptyList())
    val addresslistState: State<List<AddressItems>> = addresslist

    private val flow:MutableStateFlow<CommonUiObjectResponse<OrderIdResponse>> =  MutableStateFlow(CommonUiObjectResponse())
    var sharedFlow=flow.asStateFlow()
    private set

    init {
        getAllCartAddressItems()
        callingItemsCollectionsId(emitProductId)
    }

    fun setProductId(product_id: ProductIdIdModal) {
        emitProductId.tryEmit(product_id)
        callingItemsCollectionsId(emitProductId)
    }
    fun getFreeDeliveryMinPrice():String{
        return sharedpreferenceCommon.getMinimumDeliveryAmount()
    }

    fun getAllAddressItems() = viewModelScope.launch {
        repo.getAddressItems().catch { e -> Log.d("main", "Exception: ${e.message} ") }.collect {
            addresslist.value = it
        }

    }


    private fun getAllCartAddressItems() = viewModelScope.launch {
        repo.getCartItems().catch { e -> Log.d("main", "Exception: ${e.message} ") }.collect {
            allCartItems.value = it
        }


    }

    fun deleteCartItems(productIdNumber: String?) = viewModelScope.launch {
        repo.deleteCartItems(productIdNumber)
    }

    fun getCartItem() {
        getTotalProductItems()
        getTotalProductItemsPrice()


    }

    private fun getTotalProductItemsPrice() = viewModelScope.launch {
        repo.getTotalProductItemsPrice().catch { e -> Log.d("main", "Exception: ${e.message} ") }
            .collect {
                totalPrice.value = it ?: 0

            }
    }

    private fun getTotalProductItems() = viewModelScope.launch {
        repo.getTotalProductItems().catch { e -> Log.d("main", "Exception: ${e.message} ") }
            .collect {
                totalCount.value = it ?: 0
            }

    }

    fun getSavingAmount() = viewModelScope.launch {
        repo.getTotalSavingAmount().catch { e -> Log.d("main", "Exception: ${e.message} ") }
            .collect {
                savingAmountMutable.value = it ?: 0
            }
    }

    fun insertCartItem(
        productIdNumber: String,
        thumb: String,
        price: Int,
        productname: String,
        actualprice: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        val intger: Int = dao.getProductBasedIdCount(productIdNumber).first() ?: 0
        if (intger == 0) {
            val data = CartItems(
                productIdNumber,
                thumb,
                intger + 1,
                price,
                productname,
                actualprice,
                savingAmount = (actualprice.toInt() - price.toInt()).toString()
            )
            repo.insert(data)
        } else if (intger >= 1) {
            repo.updateCartItem(intger + 1, productIdNumber)

        }

    }

    fun deleteProduct(productIdNumber: String?) = viewModelScope.launch(Dispatchers.IO) {
        dao.deleteCartItem(productIdNumber)
    }

    fun callingItemsCollectionsId(productIdIdModal: MutableSharedFlow<ProductIdIdModal>) =
        viewModelScope.launch {
            repository.ItemsCollections(productIdIdModal.first(),sharedpreferenceCommon.getPostalCode()).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        itemsCollection.value = it.data
                    }
                    is ApiState.Failure -> {
                        itemsCollection.value = ItemsCollectionsResponse(null, it.msg.message, 401)

                    }
                    is ApiState.Loading -> {

                    }

                }
            }

        }

    fun onEvent(event: CartEvent){
        when(event ){
             is CartEvent.createOrderId->{
                 viewModelScope.launch {
                     event.request.pincode=sharedpreferenceCommon.getPostalCode()
                     repository.OrderIdRequest(event.request).doOnLoading {
                         flow.value = CommonUiObjectResponse(isLoading = true,)


                     }.doOnSuccess {
                         flow.value = CommonUiObjectResponse(data = it,)

                     }.doOnFailure {
                         flow.value = CommonUiObjectResponse(error = it?.message?:"something went wrong",)
                     }.collect()
                 }

        }

        }

    }

}
sealed class CartEvent{
    data class createOrderId(val request: OrderIdCreateRequest):CartEvent()
}
