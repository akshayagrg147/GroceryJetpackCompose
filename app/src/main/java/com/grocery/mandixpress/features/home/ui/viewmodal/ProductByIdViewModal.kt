package com.grocery.mandixpress.features.home.ui.viewmodal

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.mandixpress.Utils.showLog
import com.grocery.mandixpress.roomdatabase.CartItems
import com.grocery.mandixpress.roomdatabase.RoomRepository
import com.grocery.mandixpress.sharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.common.doOnFailure
import com.grocery.mandixpress.common.doOnLoading
import com.grocery.mandixpress.common.doOnSuccess
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.data.modal.ProductByIdResponseModal
import com.grocery.mandixpress.data.modal.ProductIdIdModal
import com.grocery.mandixpress.data.modal.RelatedSearchRequest
import com.grocery.mandixpress.features.splash.domain.repository.CommonRepository
import com.grocery.mandixpress.roomdatabase.AdminAccessTable
import com.grocery.mandixpress.roomdatabase.Dao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@HiltViewModel
class ProductByIdViewModal @Inject constructor(
    val dao: Dao,
    val sharedpreferenceCommon: sharedpreferenceCommon,
    val repository: CommonRepository,
    private val repo: RoomRepository
) : ViewModel() {
    var adminAccessTableData=AdminAccessTable()
    var cartTableData=CartItems()
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

        repo.deleteCartItems(value. homeproducts?.productId){
            if(it==0){
                updateDeliveryRate()
            }
        }
        getItemBaseOnProductId(value.homeproducts?.productId?:"")
    }
    private fun updateDeliveryRate() {
        val latLngList: MutableList<Pair<Double, Double>> = mutableListOf()
        var totalKm = 0.00

        viewModelScope.launch {
            val cartItems = dao.getAllCartItems().first()
            if(cartItems.isEmpty())
                return@launch
            val distinctSellerNames = cartItems.map { it.sellerId }.distinct()
            val sellerDetail: AdminAccessTable = dao.getSellerDetail(distinctSellerNames[0])?.first() ?: AdminAccessTable()

            if(distinctSellerNames.size>1){
                sharedpreferenceCommon.setMinimumDeliveryAmount(sellerDetail.price.toString())
                for (value in cartItems) {
                    latLngList.add(Pair(value.lat ?: 0.00, value.lng ?: 0.00))
                }
                val uniqueLatLngSet = latLngList.toSet()

                val uniqueLatLngList = uniqueLatLngSet.toList()

                for (i in 0 until uniqueLatLngList.size - 1) {
                    totalKm += haversine(
                        uniqueLatLngList[i].first,
                        uniqueLatLngList[i].second,
                        uniqueLatLngList[i + 1].first,
                        uniqueLatLngList[i + 1].second
                    )
                }
                val decimalRupees = String.format("%.2f", totalKm)
                sharedpreferenceCommon.setMinimumDeliveryAmount((sharedpreferenceCommon.getMinimumDeliveryAmount().toFloat()+(decimalRupees.toFloat()*5)).toString())

            }
            else{
                sharedpreferenceCommon.setMinimumDeliveryAmount(sellerDetail.price?:"").toString()

            }

        }

    }
    fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Earth radius in kilometers

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)

        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c
    }

    fun getItemBaseOnProductId(value: String) = viewModelScope.launch(Dispatchers.IO) {
        val intger: Int = repo.getProductBasedIdCount(value).first() ?: 0
        getItemCount.value = intger
    }
    fun getStoreAdminCartTable():Pair<AdminAccessTable,CartItems>{
        return Pair(adminAccessTableData,cartTableData)
    }
    fun tempStoreAdminCartTable(accessTable: AdminAccessTable, cartItem: CartItems) {
        adminAccessTableData=accessTable
        cartTableData=cartItem
    }

    fun insertCartItem(value: ProductByIdResponseModal,passSellerDetail:(AdminAccessTable,CartItems)->Unit) = viewModelScope.launch(Dispatchers.IO) {
        val intger: Int = repo.getProductBasedIdCount(value.homeproducts?.productId?:"").first() ?: 0

        if (intger == 0) {
            val data = CartItems(
                value.homeproducts?.productId,
                value.homeproducts?.productImage1,
                1,
                Integer.parseInt(value.homeproducts?.orignalprice ?: "0"),
                value.homeproducts?.productName,
                value.homeproducts?.orignalprice,
                savingAmount = ((value.homeproducts?.orignalprice?.toInt()?:0) - (value.homeproducts?.selling_price?.toInt()?:0)).toString(),
                sellerId = value.homeproducts?.sellerId.toString(),
            )
            if (dao.getAllCartItems().first().isEmpty()) {
                val sellerDetail: AdminAccessTable =
                    dao.getSellerDetail(value.homeproducts?.sellerId)?.first()
                        ?: AdminAccessTable()

                sharedpreferenceCommon.setMinimumDeliveryAmount(sellerDetail.price ?: "")
                data.lat=sellerDetail.latitude?.toDouble()
                data.lng=sellerDetail.longitude?.toDouble()

                repo.insert(data)
            }
            else {
                val sellerIdExist: Boolean = dao.isExistSeller(value.homeproducts?.sellerId)

                if(!sellerIdExist){
                    val sellerDetail: AdminAccessTable =
                        dao.getSellerDetail(value.homeproducts?.sellerId)?.first()
                            ?: AdminAccessTable()
                    passSellerDetail(sellerDetail,data)

                }
            }


        } else if (intger >= 1) {
            repo.updateCartItem(intger + 1, value.homeproducts?.productId?:"")

        }
        getItemBaseOnProductId(value.homeproducts?.productId?:"")


    }
//cart items price
    fun getTotalProductItemsPrice() = viewModelScope.launch {
        repo.getTotalProductItemsPrice()?.catch { e ->
            showLog("main", "Exception: ${e.message} ") }
            ?.collect {
                totalPrice.value = it ?: 0

            }
    }
//cart items count
    fun getTotalProductItems() = viewModelScope.launch {
        repo.getTotalProductItems().catch { e ->
            showLog("main", "Exception: ${e.message} ") }
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
                                    _eventRelatedSearchFlow.value = ComposeUiResponse( error = it.msg.toString())
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

    fun distancebetweenTwoLatLng(){

    }


    fun updateDeliveryCharges(data: AdminAccessTable, cartTableData: CartItems,passStoreDeliveryCharge:(Int)->Unit) {
        if(sharedpreferenceCommon.getMinimumDeliveryAmount().isNotEmpty()) {

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
        }
        else{
            passStoreDeliveryCharge(0)
        }

    }

}

sealed class ProductEvents {

    data class RelatedSearchEvents(val data: RelatedSearchRequest) : ProductEvents()
    data class ItemDetailEvent(val data: ProductIdIdModal) : ProductEvents()



}