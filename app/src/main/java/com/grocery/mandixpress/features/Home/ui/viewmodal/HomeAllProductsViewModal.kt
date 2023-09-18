package com.grocery.mandixpress.features.Home.ui.viewmodal

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.google.android.libraries.places.api.model.AutocompletePrediction
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.gson.Gson
import com.grocery.mandixpress.RoomDatabase.CartItems
import com.grocery.mandixpress.RoomDatabase.Dao
import com.grocery.mandixpress.RoomDatabase.RoomRepository
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.data.modal.*
import com.grocery.mandixpress.data.network.CallingCategoryWiseData
import com.grocery.mandixpress.data.pagingsource.PaginSoucrce
import com.grocery.mandixpress.features.Home.domain.modal.AddressItems
import com.grocery.mandixpress.features.Home.domain.modal.getProductCategory
import com.grocery.mandixpress.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.android.parcel.RawValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeAllProductsViewModal @Inject constructor(
    private val pagingDataSource: PaginSoucrce,
    val roomrespo: RoomRepository,
    val repository: CommonRepository,
    val
    sharedPreferences: sharedpreferenceCommon,
    val dao: Dao,
    @ApplicationContext context: Context,
    val cat: CallingCategoryWiseData
) : ViewModel() {
    init {
        getItemCount()
        getItemPrice()


    }

    //events object

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

    private val _categoryWiseResponse: MutableStateFlow<ComposeUiResponse<CategoryWiseDashboardResponse>> =
        MutableStateFlow(
            ComposeUiResponse()
        )
    var categoryWiseResponse = _categoryWiseResponse.asStateFlow()
        private set

    private val availibilityCheck: MutableStateFlow<ComposeUiResponse<commonResponse>> =
        MutableStateFlow(
            ComposeUiResponse()
        )
    var _availibilityCheck = availibilityCheck.asStateFlow()
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

    var filterlist: List<HomeAllProductsResponse.HomeResponse>? = null


    val repo = getitemcount11
        .debounce(300)
        .filter {
            it.trim().isEmpty().not()
        }
        .distinctUntilChanged()
        .flatMapLatest {
            repository.HomeAllProducts(it)
        }


    fun setList(response: @RawValue HomeAllProductsResponse) {

        listMutable.value = response
        globalmutablelist.value = response

    }

    fun setvalue(str: String) {
        live.value = str
        listMutable.value = listMutable.value


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
        val address = if (sharedPreferences.getSearchAddress()
                .isNotEmpty()
        ) sharedPreferences.getSearchAddress() else sharedPreferences.getCombinedAddress()
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
        when (event) {
            is  HomeEvent.BestSellingEventFlow -> viewModelScope.launch {
                repository.BestSellingProducts()
                    .collectLatest {
                        when (it) {
                            is ApiState.Loading -> {
                                _bestSelling.value = ComposeUiResponse(isLoading = true)
                            }
                            is ApiState.Success -> {
                                _bestSelling.value = ComposeUiResponse(data = it.data)
                            }
                            is ApiState.Failure -> {

                                _bestSelling.value = ComposeUiResponse(error = it?.msg.toString())
                            }
                        }
                    }

            }
             HomeEvent.ExclusiveEventFlow -> viewModelScope.launch {
                repository.ExclusiveProducts(city=sharedPreferences.getCity())
                    .collectLatest {
                        when(it){
                            is ApiState.Loading->{
                                _exclusive.value = ComposeUiResponse( isLoading = true)
                            }
                            is ApiState.Success->{
                                _exclusive.value = ComposeUiResponse(data = it.data)
                            }
                            is ApiState.Failure->{
                                _exclusive.value = ComposeUiResponse( error = it?.msg.toString())
                            }
                        }
                    }


            }
             HomeEvent.ItemCategoryEventFlow -> viewModelScope.launch {
                Log.d("checkResponse", "onEvent: called")
                repository.getProductCategory()
                    .collectLatest {
                        when(it){
                            is ApiState.Loading->{
                                _getProductCategory.value = ComposeUiResponse( isLoading = true)
                            }
                            is ApiState.Success->{
                                _getProductCategory.value = ComposeUiResponse(data = it.data)
                            }
                            is ApiState.Failure->{
                                _getProductCategory.value = ComposeUiResponse( error = it?.msg.toString())
                            }
                        }
                    }
            }
             HomeEvent.CategoryWiseEventFlow->viewModelScope.launch {
                repository.callingDasboardProducts()
                    .collectLatest {
                        when(it){
                            is ApiState.Loading->{
                                _categoryWiseResponse.value = ComposeUiResponse( isLoading = true)
                            }
                            is ApiState.Success->{
                                _categoryWiseResponse.value = ComposeUiResponse(data = it.data)
                            }
                            is ApiState.Failure->{
                                _categoryWiseResponse.value = ComposeUiResponse( error = it?.msg.toString())
                            }
                        }
                    }

            }
HomeEvent.BannerImageEventFlow->viewModelScope.launch {
    repository.bannerImageApiCall()
        .collectLatest {
            when(it){
                is ApiState.Loading->{
                    bannerImage.value = ComposeUiResponse( isLoading = true)
                }
                is ApiState.Success->{
                    bannerImage.value = ComposeUiResponse(data = it.data)
                }
                is ApiState.Failure->{
                    bannerImage.value = ComposeUiResponse( error = it?.msg.toString())
                }
            }
        }

}
            HomeEvent.AreaAvailibilityEventFlow->viewModelScope.launch {
                Log.d("postalcode","${sharedPreferences.getPostalCode()}")
                repository.availibilityCheck(sharedPreferences.getPostalCode())
                    .collectLatest {
                        when(it){
                            is ApiState.Loading->{
                                availibilityCheck.value = ComposeUiResponse( isLoading = true)
                            }
                            is ApiState.Success->{
                                availibilityCheck.value = ComposeUiResponse(data = it.data)
                            }
                            is ApiState.Failure->{
                                availibilityCheck.value = ComposeUiResponse( error = it?.msg.toString())
                            }
                        }
                    }

            }
          is  HomeEvent.BannerCategoryEventFlow->viewModelScope.launch {
                repository.ItemsCollections(ProductIdIdModal(event.subcategoryName)).collectLatest {
                    when (it) {
                        is ApiState.Success -> {
                            bannerCategoryResponse.value = ComposeUiResponse(data = it.data)
                        }
                        is ApiState.Failure -> {

                            bannerCategoryResponse.value = ComposeUiResponse(error = it?.msg.toString())

                        }
                        is ApiState.Loading -> {
                            bannerCategoryResponse.value = ComposeUiResponse(isLoading = true)
                        }

                    }
                }
            }
            else -> {}
        }
    }


    fun getItemCount() = viewModelScope.launch {

        roomrespo.getTotalProductItems().catch { e -> Log.d("dmdndnd", "Exception: ${e.message} ") }
            .collect {
                totalcount.value = it ?: 0
                Log.d("dmdndnd", totalcount.value.toString())
            }

    }

    fun getItemPrice() = viewModelScope.launch {
        roomrespo.getTotalProductItemsPrice()
            .catch { e -> Log.d("dmdndnd", "Exception: ${e.message} ") }.collect {
                totalprice.value = it ?: 0
                Log.d("dmdndnd", totalprice.value.toString())
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
            roomrespo.insert(data)
        } else if (intger >= 1) {
            roomrespo.updateCartItem(intger + 1, productIdNumber)

        }


    }

    fun setcategory(value: String) {
        cat.settingData(value)

    }

    fun setItemDataClass(bannerItemDat: BannerImageResponse.ItemData, indexValue: Int) {
            cat.setItemDataClass(bannerItemDat, indexValue)


    }


    fun getcategory(): String {
        return cat.gettingData()

    }


    fun setFilterList(filterlist: List<HomeAllProductsResponse.HomeResponse>?) {
        Log.d("kdkkdk", "skksk ${Gson().toJson(filterlist)}")
        listMutable.value = HomeAllProductsResponse(filterlist)
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

    object AreaAvailibilityEventFlow : HomeEvent()
    object BannerImageEventFlow : HomeEvent()
    data class BannerCategoryEventFlow(val subcategoryName:String) : HomeEvent()


}

data class ComposeUiResponse<T>(
    val data: T? = null,
    val error: String = "",
    val isLoading: Boolean = false
)