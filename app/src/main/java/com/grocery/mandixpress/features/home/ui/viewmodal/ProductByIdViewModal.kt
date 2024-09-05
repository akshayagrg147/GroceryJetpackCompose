package com.grocery.mandixpress.features.home.ui.viewmodal

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.mandixpress.Utils.showLog
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
import com.grocery.mandixpress.roomdatabase.CartItemPriceBySeller
import com.grocery.mandixpress.roomdatabase.CartItems
import com.grocery.mandixpress.roomdatabase.Dao
import com.grocery.mandixpress.roomdatabase.RoomRepository
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
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

    private val firsSellerLatLng: MutableState<Pair<Double,Double>> = mutableStateOf(Pair(0.00,0.00))
    val firsSellerLatLngValue: MutableState<Pair<Double,Double>> = firsSellerLatLng

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

    init {
        getFirstItemCartLatLng()
    }

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
                sharedpreferenceCommon.setMinimumDeliveryAmount(sellerDetail.price?.toFloat()?:0.00f)
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
                val decimalRupees = totalKm.toInt()
                sharedpreferenceCommon.setMinimumDeliveryAmount((sharedpreferenceCommon.getMinimumDeliveryAmount().toFloat()+(decimalRupees.toFloat()*5)))

            }
            else{
                sharedpreferenceCommon.setMinimumDeliveryAmount(sellerDetail.price?.toFloat()?:0.00f).toString()
                sharedpreferenceCommon.setDeliverySellersCharges(0.00f)
            }

        }

    }
    private fun getFirstItemCartLatLng() = viewModelScope.launch {
        repo.getCartItems().catch { e -> showLog("main", "Exception: ${e.message} ") }
            .collect {
                if(it.isNotEmpty())
                firsSellerLatLng.value = Pair(it[0].lat ?: 0.00, it[0].lng ?: 0.00,)
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
    fun getSvedLatLng():Pair<String?,String?>{
        return sharedpreferenceCommon.getLatLng()

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
    fun getSellersMinDeliveryCharge():Float{
        return sharedpreferenceCommon.getDeliverySellersCharges()
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

                sharedpreferenceCommon.setMinimumDeliveryAmount(sellerDetail.price?.toFloat() ?:0.00f )
                data.lat=sellerDetail.latitude?.toDouble()
                data.lng=sellerDetail.longitude?.toDouble()

                repo.insert(data)
                passSellerDetail(AdminAccessTable(),CartItems())
            }
            else {
                val sellerIdExist: Boolean = dao.isExistSeller(value.homeproducts?.sellerId)

                if(!sellerIdExist){
                    val sellerDetail: AdminAccessTable =
                        dao.getSellerDetail(value.homeproducts?.sellerId)?.first()
                            ?: AdminAccessTable()
                    passSellerDetail(sellerDetail,data)

                }
                else{
                    val sellerDetail: AdminAccessTable = dao.getSellerDetail(value.homeproducts?.sellerId)?.first() ?: AdminAccessTable()

                    val withHighestCartItemTotal=dao.getSellerWithHighestCartItemTotal()
                    val sellerPickMinDelivery: AdminAccessTable = dao.getSellerDetail( withHighestCartItemTotal.get(0)?.sellerId)?.first() ?: AdminAccessTable()


                    sharedpreferenceCommon.setMinimumDeliveryAmount(sellerPickMinDelivery.price?.toFloat()?:0.00f)

                    data.lat=sellerDetail.latitude?.toDouble()
                    data.lng=sellerDetail.longitude?.toDouble()
                    repo.insert(data)
                    passSellerDetail(AdminAccessTable(),CartItems())

                }
            }


        } else if (intger >= 1) {
            val withHighestCartItemTotal=dao.getSellerWithHighestCartItemTotal()
            val sellerPickMinDelivery: AdminAccessTable = dao.getSellerDetail( withHighestCartItemTotal.get(0)?.sellerId)?.first() ?: AdminAccessTable()
            sharedpreferenceCommon.setMinimumDeliveryAmount(sellerPickMinDelivery.price?.toFloat()?:0.00f)

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
    suspend fun withHigherCartItemTotal(): ArrayList<CartItemPriceBySeller> {
        return withContext(Dispatchers.IO) {
            val lsItems: ArrayList<CartItemPriceBySeller> = ArrayList()
            val resultList = dao.getSellerWithHighestCartItemTotal()
            resultList.forEach { seller ->
                val sellerPickMinDelivery: AdminAccessTable = dao.getSellerDetail(seller?.sellerId)?.first() ?: AdminAccessTable()
                lsItems.add(CartItemPriceBySeller(seller?.sellerId, seller?.totalItemPrice, sellerPickMinDelivery.price?.toInt()))
            }
            lsItems
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
    fun getFreeDeliveryMinPrice():Double{
        return sharedpreferenceCommon.getMinimumDeliveryAmount().toDouble()
    }

    fun distancebetweenTwoLatLng(){

    }




    fun updateDeliveryCharges(data: AdminAccessTable, cartTableData: CartItems,passStoreDeliveryCharge:(CartItems)->Unit) {

        viewModelScope.launch(Dispatchers.IO) {
            cartTableData.lat=data.latitude?.toDouble()
            cartTableData.lng=data.longitude?.toDouble()

        }
        passStoreDeliveryCharge(cartTableData)





    }

    fun getDeliveryChargeBasesOnLatLng(dataincart:CartItems, callback: (Double) -> Unit) {
        val latLngList: MutableList<Pair<Double, Double>> = mutableListOf()
        var totalKm = 0.00

        viewModelScope.launch {
            val cartItems = dao.getAllCartItems().first()
            val resultMutableList: MutableList<CartItems> = cartItems.toMutableList()
            resultMutableList.add(dataincart)

            resultMutableList.forEach { value ->
                latLngList.add(Pair(value.lat ?: 0.0, value.lng ?: 0.0))
            }

            val uniqueLatLngList = latLngList.distinct()

            for (i in 0 until uniqueLatLngList.size - 1) {
                totalKm += haversine(
                    uniqueLatLngList[i].first,
                    uniqueLatLngList[i].second,
                    uniqueLatLngList[i + 1].first,
                    uniqueLatLngList[i + 1].second
                )
            }
            val decimalRupees = String.format("%.2f", totalKm)
            val deliveryCharge = decimalRupees.toFloat() * 5
            sharedpreferenceCommon.setDeliverySellersCharges(deliveryCharge)
            // Adjust minimum delivery amount
            val minimumDeliveryAmount = sharedpreferenceCommon.getMinimumDeliveryAmount().toFloat()
            sharedpreferenceCommon.setMinimumDeliveryAmount((minimumDeliveryAmount + deliveryCharge))

            showLog("getDeliveryChargeB", " $totalKm ---${decimalRupees}---${deliveryCharge} ${sharedpreferenceCommon.getMinimumDeliveryAmount()}---$totalKm---${latLngList.size}---${sharedpreferenceCommon.getDeliverySellersCharges()}")
            repo.insert(cartTableData)
            callback(totalKm)
        }
    }

}

sealed class ProductEvents {

    data class RelatedSearchEvents(val data: RelatedSearchRequest) : ProductEvents()
    data class ItemDetailEvent(val data: ProductIdIdModal) : ProductEvents()



}