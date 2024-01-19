package com.grocery.mandixpress.features.home.ui.viewmodal

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.mandixpress.roomdatabase.CartItems
import com.grocery.mandixpress.roomdatabase.Dao
import com.grocery.mandixpress.roomdatabase.RoomRepository
import com.grocery.mandixpress.sharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.Utils.Constants
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.common.doOnFailure
import com.grocery.mandixpress.common.doOnLoading
import com.grocery.mandixpress.common.doOnSuccess
import com.grocery.mandixpress.data.modal.ItemsCollectionsResponse
import com.grocery.mandixpress.data.modal.OrderIdCreateRequest
import com.grocery.mandixpress.data.modal.OrderIdResponse
import com.grocery.mandixpress.data.modal.ProductIdIdModal
import com.grocery.mandixpress.features.home.domain.modal.AddressItems
import com.grocery.mandixpress.features.splash.domain.repository.CommonRepository
import com.grocery.mandixpress.notification.model.NotificationDataModel
import com.grocery.mandixpress.notification.model.NotificationModel
import com.grocery.mandixpress.roomdatabase.AdminAccessTable
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
    private val itemsCollection: MutableState<ItemsCollectionsResponse> =
        mutableStateOf(ItemsCollectionsResponse(null, null, null))

    var _itemsCollection: State<ItemsCollectionsResponse> = itemsCollection


var listOfAllItems= mutableListOf<ItemsCollectionsResponse.SubItems>()
    private val allItemsCollection: MutableStateFlow<CommonUiObjectResponse<ItemsCollectionsResponse>> =
        MutableStateFlow(CommonUiObjectResponse())
    var _allItemsCollection = allItemsCollection.asStateFlow()
        private set


    private val totalCount: MutableState<Int> = mutableStateOf(0)
    val totalCountState: State<Int> = totalCount
    private val totalPrice: MutableState<Int> =
        mutableStateOf(0)
    val totalPriceState: State<Int> = totalPrice
    var adminAccessTableData = AdminAccessTable()
    var cartTableData = CartItems()

    private val addresslist: MutableState<List<AddressItems>> = mutableStateOf(emptyList())
    val addresslistState: State<List<AddressItems>> = addresslist

    private val createOrderIdMS: MutableStateFlow<CommonUiObjectResponse<OrderIdResponse>> =
        MutableStateFlow(CommonUiObjectResponse())
    var createOrderIdState = createOrderIdMS.asStateFlow()
        private set

    init {
        getAllCartAddressItems()
        callingItemsCollectionsId(emitProductId)
    }

    fun setProductId(product_id: ProductIdIdModal) {
        emitProductId.tryEmit(product_id)
        callingItemsCollectionsId(emitProductId)
    }

    fun getFreeDeliveryMinPrice(): String {
        return sharedpreferenceCommon.getMinimumDeliveryAmount()
    }

    fun getAllAddressItems() = viewModelScope.launch {
        repo.getAddressItems().catch { e -> Log.d("main", "Exception: ${e.message} ") }
            .collect {
                val customAddress = "Custom Address"

                val updatedList = mutableListOf<AddressItems>(AddressItems("", "", 1, "", "", ""))
                updatedList.addAll(it)
                addresslist.value = updatedList.reversed()
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
        repo.getTotalProductItemsPrice()?.catch { e -> Log.d("main", "Exception: ${e.message} ") }
            ?.collect {
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
        repo.getTotalSavingAmount()?.catch { e -> Log.d("main", "Exception: ${e.message} ") }
            ?.collect {
                savingAmountMutable.value = it ?: 0
            }
    }

    fun insertCartItemCartScreen(
        productIdNumber: String,
        thumb: String,
        price: Int,
        productname: String,
        actualprice: String,
        sellerId:String
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
                savingAmount = (actualprice.toInt() - price.toInt()).toString(),
                sellerId = sellerId
            )
            repo.insert(data)
        } else if (intger >= 1) {
            repo.updateCartItem(intger + 1, productIdNumber)

        }

    }
    fun insertCartItem(
        productIdNumber: String,
        thumb: String,
        price: Int,
        productname: String,
        actualprice: String,
        sellerId: String,
        passSellerDetail: (AdminAccessTable, CartItems) -> Unit
    ) = viewModelScope.launch(Dispatchers.IO) {

        val intger: Int = dao.getProductBasedIdCount(productIdNumber).first() ?: 0
        val data = CartItems(
            productIdNumber,
            thumb,
            intger + 1,
            price,
            productname,
            actualprice,
            savingAmount = (actualprice.toInt() - price.toInt()).toString(),
            sellerId = sellerId
        )
        if (intger == 0) {
            if (dao.getAllCartItems().first().isEmpty()) {
                val sellerDetail: AdminAccessTable =
                    dao.getSellerDetail(sellerId)?.first() ?: AdminAccessTable()

                sharedpreferenceCommon.setMinimumDeliveryAmount(sellerDetail.price ?: "")
                data.lat=sellerDetail.latitude?.toDouble()
                data.lng=sellerDetail.longitude?.toDouble()
                repo.insert(data)
            } else {
                val sellerIdExist: Boolean = dao.isExistSeller(sellerId)

                if (!sellerIdExist) {
                    val sellerDetail: AdminAccessTable =
                        dao.getSellerDetail(sellerId)?.first() ?: AdminAccessTable()
                    passSellerDetail(sellerDetail, data)
                }
            }


        } else if (intger >= 1) {
            repo.updateCartItem(intger + 1, productIdNumber)

        }

    }

    fun deleteProduct(productIdNumber: String?) = viewModelScope.launch(Dispatchers.IO) {
        dao.deleteCartItem(productIdNumber)
    }

    fun callingItemsCollectionsId(productIdIdModal: MutableSharedFlow<ProductIdIdModal>) =
        viewModelScope.launch {
            repository.ItemsCollections(
                productIdIdModal.first(),
                sharedpreferenceCommon.getPostalCode()
            ).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        itemsCollection.value = it.data
                        Log.d("itemsizevalue","${itemsCollection.value.list?.size}")
                    }

                    is ApiState.Failure -> {
                        itemsCollection.value = ItemsCollectionsResponse(null, it.msg.message, 401)

                    }

                    is ApiState.Loading -> {

                    }

                }
            }

        }

    fun onEvent(event: CartEvent) {
        when (event) {
            is CartEvent.createOrderId -> {
                viewModelScope.launch {
                    event.request.pincode = sharedpreferenceCommon.getPostalCode()
                    event.request.fcm_token = sharedpreferenceCommon.getFcmToken()
                    Log.d("orderIdRequest", "${event.request}")
                    repository.OrderIdRequest(event.request).doOnLoading {
                        createOrderIdMS.value = CommonUiObjectResponse(isLoading = true,)


                    }.doOnSuccess {
                        createOrderIdMS.value = CommonUiObjectResponse(data = it,)

                    }.doOnFailure {
                        createOrderIdMS.value =
                            CommonUiObjectResponse(error = it?.message ?: "something went wrong",)
                    }.collect()
                }

            }
            is CartEvent.dataFetchBasedOnMainCategory -> {
                viewModelScope.launch {

                    repository.dataFetchBasedOnMainCategory(event.request).doOnLoading {
                        allItemsCollection.value = CommonUiObjectResponse(isLoading = true,)


                    }.doOnSuccess {
                        listOfAllItems=it.list?.toMutableList()?: mutableListOf()
                        allItemsCollection.value = CommonUiObjectResponse(data = it,)

                    }.doOnFailure {
                        allItemsCollection.value =   CommonUiObjectResponse(error = it?.message ?: "something went wrong",)
                    }.collect()
                }

            }



        }

    }


    fun sendNotification(fcm: String) {
        viewModelScope.launch {
            repository.postNotification(
                NotificationModel(
                    data = NotificationDataModel(
                        title = "Order Received",
                        message = "check your order list"
                    ),
                    to = fcm.ifBlank { Constants.TOPIC }
                )).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        Log.d("notificationsend", "sent")
                    }

                    is ApiState.Failure -> {
                        Log.d("notificationsend", "${it.msg}")
                    }

                    else -> {

                    }
                }
            }
        }

    }

    fun getStoreAdminCartTable(): Pair<AdminAccessTable, CartItems> {
        return Pair(adminAccessTableData, cartTableData)
    }

    fun tempStoreAdminCartTable(accessTable: AdminAccessTable, cartItem: CartItems) {
        adminAccessTableData = accessTable
        cartTableData = cartItem
    }


    fun updateDeliveryCharges(
        data: AdminAccessTable,
        cartTableData: CartItems,
        passStoreDeliveryCharge: (Int) -> Unit
    ) {
        if (sharedpreferenceCommon.getMinimumDeliveryAmount().isNotEmpty()) {

            sharedpreferenceCommon.setMinimumDeliveryAmount(
                (sharedpreferenceCommon.getMinimumDeliveryAmount()
                    .toInt() + (data.price?.toInt() ?: 0)).toString()
            )
            viewModelScope.launch(Dispatchers.IO) {
                cartTableData.lat=data.latitude?.toDouble()
                cartTableData.lng=data.longitude?.toDouble()

                repo.insert(cartTableData)
            }
            passStoreDeliveryCharge(1)
        } else {
            passStoreDeliveryCharge(0)
        }

    }



    fun searchCharcterWiseItem(value: String) {
        Log.d("itemsizevalue", "${allItemsCollection.value.data?.list?.size}")

        if (value.isNotEmpty()) {
            val filteredList = listOfAllItems.filter {
                it.productName.contains(value, true)
            } ?: emptyList()

            val updatedData = allItemsCollection.value.data?.copy(list = filteredList)
            allItemsCollection.value = allItemsCollection.value.copy(data = updatedData)

            Log.d("itemsizevalue", "filter ${_allItemsCollection.value.data?.list?.size}")
        } else {
            // If the search value is empty, reset _allItemsCollection to the original data
           // allItemsCollection.value = allItemsCollection.value?.copy(data = listOfAllItems)

           // Log.d("itemsizevalue", "all ${allItemsCollection.value.data?.list?.size}")
        }
    }

}
sealed class CartEvent{
    data class createOrderId(val request: OrderIdCreateRequest):CartEvent()
    data class dataFetchBasedOnMainCategory(val request: ProductIdIdModal):CartEvent()


}
