package com.grocery.mandixpress.features.home.ui.viewmodal

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.Gson
import com.grocery.mandixpress.roomdatabase.CartItems
import com.grocery.mandixpress.roomdatabase.Dao
import com.grocery.mandixpress.roomdatabase.RoomRepository
import com.grocery.mandixpress.sharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.data.modal.*
import com.grocery.mandixpress.data.network.CallingCategoryWiseData
import com.grocery.mandixpress.data.pagingsource.PaginSoucrce
import com.grocery.mandixpress.di.NetworkModule
import com.grocery.mandixpress.features.home.domain.modal.AddressItems
import com.grocery.mandixpress.features.home.domain.modal.getProductCategory
import com.grocery.mandixpress.features.splash.domain.repository.CommonRepository
import com.grocery.mandixpress.roomdatabase.AdminAccessTable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.android.parcel.RawValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

@HiltViewModel
class HomeAllProductsViewModal @Inject constructor(
    private val pagingDataSource: PaginSoucrce,
    val roomrespo: RoomRepository,
    val repository: CommonRepository,
    var databaseClearer: NetworkModule.DatabaseClearer,
    val
    sharedPreferences: sharedpreferenceCommon,
    val dao: Dao,
    private val cat: CallingCategoryWiseData,

    ) : ViewModel() {
    init {
        registerFcmToken()
        getItemCount()
        getItemPrice()


    }
    var adminAccessTableData=AdminAccessTable()
    var cartTableData=CartItems()
    //events object
    var myParcelableData: HomeAllProductsResponse? by mutableStateOf(HomeAllProductsResponse())
    private val _bestSelling: MutableStateFlow<ComposeUiResponse<HomeAllProductsResponse>> =
        MutableStateFlow(
            ComposeUiResponse()
        )
    var bestSelling = _bestSelling.asStateFlow()
        private set


    private val _exclusive: MutableStateFlow<ComposeUiResponse<HomeAllProductsResponse>> =
        MutableStateFlow(
            ComposeUiResponse()
        )
    var exclusive = _exclusive.asStateFlow()
        private set


    private val sellerList: MutableStateFlow<ComposeUiResponse<AdminResponse>> =
        MutableStateFlow(
            ComposeUiResponse()
        )
    var _sellerList = sellerList.asStateFlow()
        private set

    private val _categoryWiseResponse: MutableStateFlow<ComposeUiResponse<CategoryWiseDashboardResponse>> =
        MutableStateFlow(
            ComposeUiResponse()
        )
    var categoryWiseResponse = _categoryWiseResponse.asStateFlow()
        private set



    private val bannerImage: MutableStateFlow<ComposeUiResponse<BannerImageResponse>> =
        MutableStateFlow(
            ComposeUiResponse()
        )
    var _bannerImage = bannerImage.asStateFlow()
        private set

    private val bannerCategoryResponse: MutableStateFlow<ComposeUiResponse<ItemsCollectionsResponse>> =
        MutableStateFlow(
            ComposeUiResponse()
        )
    var _bannerCategoryResponse = bannerCategoryResponse.asStateFlow()
        private set

    private val _getProductCategory: MutableStateFlow<ComposeUiResponse<getProductCategory>> =
        MutableStateFlow(
            ComposeUiResponse()
        )
    var getProductCategory = _getProductCategory.asStateFlow()
        private set


    private val addresslist: MutableState<List<AddressItems>> = mutableStateOf(emptyList())
    val list: State<List<AddressItems>> = addresslist

    private val totalcount: MutableState<Int> = mutableStateOf(0)
    val getitemcountState: MutableState<Int> = totalcount
    private val totalprice: MutableState<Int> = mutableStateOf(0)
    val getitempriceState: MutableState<Int> = totalprice

    private val m11: MutableStateFlow<String> = MutableStateFlow("")
    val getitemcount11 = m11.asStateFlow()
    private val live: MutableState<String> = mutableStateOf("")
    val responseLiveData: MutableState<String> = live

    private var listMutable: MutableState<HomeAllProductsResponse> = mutableStateOf(
        HomeAllProductsResponse()
    )
    private var globalmutablelist: MutableState<HomeAllProductsResponse> = mutableStateOf(
        HomeAllProductsResponse()
    )
    val globalmutablelist1: State<HomeAllProductsResponse> = globalmutablelist
    val listState: State<HomeAllProductsResponse> = listMutable

    val predictions: MutableStateFlow<List<AutocompletePrediction>> = MutableStateFlow(emptyList())


    val repo = getitemcount11
        .debounce(300)
        .filter {
            it.trim().isEmpty().not()
        }
        .distinctUntilChanged()
        .flatMapLatest {
            repository.HomeAllProducts(it,sharedPreferences.getPostalCode())
        }


    fun setList(response: @RawValue HomeAllProductsResponse) {

        listMutable.value = response
        globalmutablelist.value = response

    }
    fun getFreeDeliveryMinPrice():String{
        return sharedPreferences.getMinimumDeliveryAmount()
    }

    fun setvalue(str: String) {
        live.value = str
        listMutable.value = listMutable.value


    }

    fun registerFcmToken() {
        Log.d("sharedreference", "success ${sharedPreferences.getMobileNumber()}")

        viewModelScope.launch {


            repository.registerUserToken(
                sharedPreferences.getFcmToken(),
                sharedPreferences.getMobileNumber().replace("+","")
            ).collectLatest {
                when (it) {
                    is ApiState.Success -> {
                        Log.d("sharedreference", "success")
                    }
                    is ApiState.Failure -> {
                        Log.d("sharedreference", "failure ${it.msg}")
                    }
                }
            }

        }
    }


    fun searchAddress(
        query: String,
        placesClient: PlacesClient
    ): Flow<List<AutocompletePrediction>> {
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(query)
            .build()


        placesClient.findAutocompletePredictions(request).addOnSuccessListener { response ->
            viewModelScope.launch {
                predictions.emit(response.autocompletePredictions)
            }
        }
        return predictions
    }

    fun setupFlow(query: String) {
        m11.value = query

    }

    fun gettingAddres(): String {
        val address = sharedPreferences.getSearchAddress().ifEmpty { sharedPreferences.getCombinedAddress() }
        return address
    }

    suspend fun getAddress() = withContext(Dispatchers.IO) {
        dao.getAllAddress().collectLatest {
            addresslist.value = it
        }


    }

    fun deleteAddress(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        dao.deleteAddress(id.toString())

    }

    fun deleteCartItems() = viewModelScope.launch(Dispatchers.IO) {
        dao.deleteAllFromTable()
    }

    fun onEvent(event: HomeEvent) {
        Log.d("showinggetfcmtoken", sharedPreferences.getFcmToken())
        when (event) {
            is  HomeEvent.BestSellingEventFlow -> viewModelScope.launch {
                repository.BestSellingProducts(sharedPreferences.getPostalCode())
                    .collectLatest {
                        when (it) {
                            is ApiState.Loading -> {
                                _bestSelling.value = ComposeUiResponse(isLoading = true)
                            }
                            is ApiState.Success -> {
                                _bestSelling.value = ComposeUiResponse(data = it.data)
                            }
                            is ApiState.Failure -> {

                                _bestSelling.value = ComposeUiResponse(error = it.msg.toString())
                            }
                        }
                    }

            }
             HomeEvent.ExclusiveEventFlow -> viewModelScope.launch {
                repository.ExclusiveProducts(city=sharedPreferences.getCity(), pincode = sharedPreferences.getPostalCode())
                    .collectLatest {
                        when(it){
                            is ApiState.Loading->{
                                _exclusive.value = ComposeUiResponse( isLoading = true)
                            }
                            is ApiState.Success->{
                                _exclusive.value = ComposeUiResponse(data = it.data)
                            }
                            is ApiState.Failure->{
                                _exclusive.value = ComposeUiResponse( error = it.msg.toString())
                            }
                        }
                    }


            }
             HomeEvent.ItemCategoryEventFlow -> viewModelScope.launch {
                repository.getProductCategory(sharedPreferences.getPostalCode())
                    .collectLatest {
                        when(it){
                            is ApiState.Loading->{
                                _getProductCategory.value = ComposeUiResponse( isLoading = true)
                            }
                            is ApiState.Success->{
                                _getProductCategory.value = ComposeUiResponse(data = it.data)
                            }
                            is ApiState.Failure->{
                                _getProductCategory.value = ComposeUiResponse( error = it.msg.toString())
                            }
                        }
                    }
            }
             HomeEvent.CategoryWiseEventFlow->viewModelScope.launch {
                repository.callingDasboardProducts(sharedPreferences.getPostalCode())
                    .collectLatest {
                        when(it){
                            is ApiState.Loading->{
                                _categoryWiseResponse.value = ComposeUiResponse( isLoading = true)
                            }
                            is ApiState.Success->{
                                _categoryWiseResponse.value = ComposeUiResponse(data = it.data)
                            }
                            is ApiState.Failure->{
                                _categoryWiseResponse.value = ComposeUiResponse( error = it.msg.toString())
                            }
                        }
                    }

            }
HomeEvent.BannerImageEventFlow->viewModelScope.launch {
    repository.bannerImageApiCall(sharedPreferences.getPostalCode())
        .collectLatest {
            when(it){
                is ApiState.Loading->{
                    bannerImage.value = ComposeUiResponse( isLoading = true)
                }
                is ApiState.Success->{
                    bannerImage.value = ComposeUiResponse(data = it.data)
                }
                is ApiState.Failure->{
                    bannerImage.value = ComposeUiResponse( error = it.msg.toString())
                }
            }
        }

}
            HomeEvent.adminDetailsEventFlow->viewModelScope.launch {
                repository.getAdminDetails()
                    .collectLatest {
                        when(it){
                            is ApiState.Loading->{

                            }
                            is ApiState.Success->{
                                sellerList.value = ComposeUiResponse(data = it.data)
                                roomrespo.deleteAdminAccessItems()
                                val listPinCodeStateModal= mutableListOf<PinCodeStateModal>()
                                for(modal in it.data.itemData){
                                    if(sharedPreferences.getPostalCode()==modal.pincode){
                                        sharedPreferences.setDeliveryContactNumber(modal.deliveryContactNumber)
                                    }
                                    listPinCodeStateModal.add(PinCodeStateModal(modal.pincode,modal.city?:""))
                                    roomrespo.insertAdminDetails(AdminAccessTable(pincode = modal.pincode, price = modal.price, city = modal.city, sellerId = modal.sellerId, deliveryContactNumber = modal.deliveryContactNumber,latitude =modal.lat, longitude=modal.lng))
                                }
                           sharedPreferences.setAvailablePinCode(listPinCodeStateModal)

                            }
                            is ApiState.Failure->{
                                sellerList.value = ComposeUiResponse(error = it.msg.toString())

                            }
                        }
                    }

            }
          is  HomeEvent.BannerCategoryEventFlow->viewModelScope.launch {
                repository.ItemsCollections(ProductIdIdModal(event.subcategoryName),sharedPreferences.getPostalCode()).collectLatest {
                    when (it) {
                        is ApiState.Success -> {
                            bannerCategoryResponse.value = ComposeUiResponse(data = it.data)
                        }
                        is ApiState.Failure -> {

                            bannerCategoryResponse.value = ComposeUiResponse(error = it.msg.toString())

                        }
                        is ApiState.Loading -> {
                            bannerCategoryResponse.value = ComposeUiResponse(isLoading = true)
                        }

                    }
                }
            }
        }
    }


    fun getItemCount() = viewModelScope.launch {

        roomrespo.getTotalProductItems().catch { e -> Log.d("dmdndnd", "Exception: ${e.message} ") }
            .collect {
                totalcount.value = it ?: 0
            }

    }

    fun getItemPrice() = viewModelScope.launch {
        roomrespo.getTotalProductItemsPrice()
            ?.catch { e -> Log.d("dmdndnd", "Exception: ${e.message} ") }?.collect {

                   totalprice.value = it ?: 0


            }
    }

    fun insertCartItem(
        productIdNumber: String,
        thumb: String,
        price: Int,
        productname: String,
        actualprice: String,
        sellerId:String,
        passSellerDetail:(AdminAccessTable,CartItems)->Unit
    ) = viewModelScope.launch(Dispatchers.IO) {

        val intger: Int = dao.getProductBasedIdCount(productIdNumber).first() ?: 0
        if (intger == 0) { val data = CartItems(
                productIdNumber,
                thumb,
                intger + 1,
                price,
                productname,
                actualprice,
                savingAmount = (actualprice.toInt() - price).toString(),
                sellerId = sellerId,
            )

                if(dao.getAllCartItems().first().isEmpty())
                {   val sellerDetail: AdminAccessTable = dao.getSellerDetail(sellerId)?.first() ?: AdminAccessTable()

                    sharedPreferences.setMinimumDeliveryAmount(sellerDetail.price ?:"")
                    data.lat=sellerDetail.latitude?.toDouble()
                    data.lng=sellerDetail.longitude?.toDouble()
                    roomrespo.insert(data)

                }
                else{
                    val sellerIdExist: Boolean = dao.isExistSeller(sellerId)

                    if(!sellerIdExist){
                        val sellerDetail: AdminAccessTable = dao.getSellerDetail(sellerId)?.first() ?: AdminAccessTable()
                        passSellerDetail(sellerDetail,data)
                    }
                    else{
                        val sellerDetail: AdminAccessTable = dao.getSellerDetail(sellerId)?.first() ?: AdminAccessTable()

                        data.lat=sellerDetail.latitude?.toDouble()
                        data.lng=sellerDetail.longitude?.toDouble()
                        roomrespo.insert(data)
                        passSellerDetail(AdminAccessTable(),CartItems())

                    }
                }


        } else if (intger >= 1) {
            roomrespo.updateCartItem(intger + 1, productIdNumber)

        }


    }

    fun setcategory(value: String) {
        cat.settingData(value,sharedPreferences.getPostalCode())

    }

    fun setItemDataClass(bannerItemDat: BannerImageResponse.ItemData, indexValue: Int) {
            cat.setItemDataClass(bannerItemDat, indexValue,sharedPreferences.getPostalCode())


    }


    fun getcategory(): String {
        return cat.gettingData()

    }


    fun setFilterList(filterlist: List<HomeAllProductsResponse.HomeResponse>?) {
        Log.d("kdkkdk", "skksk ${Gson().toJson(filterlist)}")
        listMutable.value = HomeAllProductsResponse(filterlist)
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
    fun getDeliveryChargeBasesOnLatLng(callback: (Double) -> Unit) {
        val latLngList: MutableList<Pair<Double, Double>> = mutableListOf()
        var totalKm = 0.00

        viewModelScope.launch {
            val cartItems = dao.getAllCartItems().first()

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
          sharedPreferences.setMinimumDeliveryAmount((sharedPreferences.getMinimumDeliveryAmount().toFloat()+(decimalRupees.toFloat()*5)).toString())
            Log.d("getDeliveryChargeB","${sharedPreferences.getMinimumDeliveryAmount()}---$totalKm---"+latLngList.size)


            callback(totalKm)
        }
    }
     fun updateDeliveryCharges(data: AdminAccessTable, cartTableData: CartItems,passStoreDeliveryCharge:(Int)->Unit) {

           viewModelScope.launch(Dispatchers.IO) {
               cartTableData.lat=data.latitude?.toDouble()
               cartTableData.lng=data.longitude?.toDouble()

               roomrespo.insert(cartTableData)
           }
           passStoreDeliveryCharge(1)



    }

    suspend fun clearDatabase() {
        databaseClearer.clearDatabase()
    }
fun getStoreAdminCartTable():Pair<AdminAccessTable,CartItems>{
    return Pair(adminAccessTableData,cartTableData)
}
    fun tempStoreAdminCartTable(accessTable: AdminAccessTable, cartItem: CartItems) {
        adminAccessTableData=accessTable
        cartTableData=cartItem
    }


    val allresponse: Flow<PagingData<HomeAllProductsResponse.HomeResponse>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 10
            ), pagingSourceFactory = {
                pagingDataSource
            }).flow.cachedIn(viewModelScope)





}


sealed class HomeEvent {
    object ExclusiveEventFlow : HomeEvent()
    object BestSellingEventFlow : HomeEvent()
    object ItemCategoryEventFlow:HomeEvent()
    object CategoryWiseEventFlow:HomeEvent()

    object adminDetailsEventFlow : HomeEvent()
    object BannerImageEventFlow : HomeEvent()
    data class BannerCategoryEventFlow(val subcategoryName:String) : HomeEvent()


}

data class ComposeUiResponse<T>(
    val data: T? = null,
    val error: String = "",
    val isLoading: Boolean = false
)