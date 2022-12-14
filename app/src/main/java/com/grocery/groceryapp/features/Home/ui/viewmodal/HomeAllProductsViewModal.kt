package com.grocery.groceryapp.features.Spash.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.grocery.groceryapp.RoomDatabase.CartItems
import com.grocery.groceryapp.RoomDatabase.Dao
import com.grocery.groceryapp.RoomDatabase.RoomRepository
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.Utils.Constants.Companion.NETWORK_PAGE_SIZE
import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.data.modal.CategoryWiseDashboardResponse
import com.grocery.groceryapp.data.modal.HomeAllProductsResponse
import com.grocery.groceryapp.data.network.ApiService
import com.grocery.groceryapp.data.network.CallingCategoryWiseData
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.android.parcel.RawValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeAllProductsViewModal @Inject constructor(
    private val pagingDataSource: PaginSoucrce,val roomrespo: RoomRepository,
    val repository: CommonRepository, val
    sharedPreferences: sharedpreferenceCommon, val dao: Dao, @ApplicationContext context: Context, val cat:CallingCategoryWiseData
) : ViewModel() {
    init {
       callingBestSelling(sharedPreferences.getCity())
        callingExcusiveProducts(sharedPreferences.getCity())
    }

    var passingdata: MutableLiveData<List<HomeAllProductsResponse.HomeResponse>> = MutableLiveData()
    private val exclusiveProductsResponse: MutableState<HomeAllProductsResponse> =
        mutableStateOf(HomeAllProductsResponse(null, null, null))

    private val CategoryWiseDashboardResponse: MutableState<CategoryWiseDashboardResponse> =
        mutableStateOf(CategoryWiseDashboardResponse(null, null, null))
    val CategoryWiseDashboardRespon: State<CategoryWiseDashboardResponse> = CategoryWiseDashboardResponse


    private val bestsellingProductsResponse: MutableState<HomeAllProductsResponse> =
        mutableStateOf(HomeAllProductsResponse(null, null, null))

    val exclusiveProductsResponse1: State<HomeAllProductsResponse> = exclusiveProductsResponse
    val bestsellingProductsResponse1: State<HomeAllProductsResponse> = bestsellingProductsResponse

    private val addresslist: MutableState<List<AddressItems>> = mutableStateOf(emptyList())
    val list: State<List<AddressItems>> = addresslist

    private val totalcount: MutableState<Int> = mutableStateOf(0)
    val getitemcountState: MutableState<Int> = totalcount
    private val totalprice: MutableState<Int> = mutableStateOf(0)
    val getitempriceState: MutableState<Int> = totalprice

    private val m11: MutableStateFlow<String> = MutableStateFlow("")
    val getitemcount11 = m11.asSharedFlow()
    private val live:MutableState<String> = mutableStateOf("")
    val responseLiveData:MutableState<String> =live

    private var listMutable:MutableState< HomeAllProductsResponse> = mutableStateOf(
        HomeAllProductsResponse()
    )
    val listState:State<HomeAllProductsResponse> =listMutable

var filterlist:List<HomeAllProductsResponse.HomeResponse>?=null


    val repo = getitemcount11
        .debounce(300)
        .filter {
            it.trim().isEmpty().not()
        }
        .distinctUntilChanged()
        .flatMapLatest {
            repository.HomeAllProducts(it)
        }


    fun setList(response: @RawValue HomeAllProductsResponse){

        listMutable.value=response

    }

    fun setvalue(str:String){
        live.value=str
        listMutable.value=   listMutable.value


    }
    fun setupFlow(query: String){
        m11.value=query

    }

    fun gettingAddres(): String {
        return sharedPreferences.getCombinedAddress()
    }

    suspend fun getAddress() = withContext(Dispatchers.IO) {
        dao.getAllAddress().collectLatest {
            addresslist.value = it
        }


    }

    fun deleteAddress(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        dao.deleteAddress(id.toString())

    }
    fun deleteCartItems()=viewModelScope.launch(Dispatchers.IO) {
        dao.deleteAllFromTable()
    }
fun getCartItem(){
    viewModelScope.launch() {

        var totalcount1: Int = 0
        var totalPrice1: Int = 0

        withContext(Dispatchers.IO) {

            totalPrice1 = dao.getTotalProductItemsPrice()?.first()?:0
            totalcount1 = dao.getTotalProductItems()?.first()?:0
        }


        totalprice.value = totalPrice1
        totalcount.value = totalcount1


    }
}



    fun callingExcusiveProducts(city: String) = viewModelScope.launch {
        repository.ExclusiveProducts(city).collectLatest {
            when (it) {
                is ApiState.Success -> {
                    exclusiveProductsResponse.value = it.data
                }
                is ApiState.Failure -> {
                    exclusiveProductsResponse.value =
                        HomeAllProductsResponse(null, "Something went wrong", 401)
                }
                is ApiState.Loading -> {

                }
            }
        }
    }

    fun insertCartItem(
        productIdNumber: String,
        thumb: String,
        price: Int,
        productname: String,
        actualprice: String
    ) = viewModelScope.launch(Dispatchers.IO) {
       // roomrespo.insert()
        val intger: Int = dao.getProductBasedIdCount(productIdNumber).first()?:0

        if (intger == 0) {
            dao
                .insertCartItem(
                    CartItems(
                        productIdNumber,
                        thumb, intger + 1,
                        price, productname, actualprice, savingAmount = (actualprice.toInt()-price.toInt()).toString()
                    )

                )

        } else if (intger >= 1) {
            dao.updateCartItem(intger + 1, productIdNumber)

        }
        Log.d("printvalue",(intger+1).toString())


    }
    fun setcategory(ss:String){
        cat.settingData(ss)

    }
    fun getcategory():String{
       return cat.gettingData()

    }


    fun gettingData(): LiveData<List<HomeAllProductsResponse.HomeResponse>> {
        return passingdata

    }

    fun passingData(list: List<HomeAllProductsResponse.HomeResponse>) {
        passingdata.value = list

    }

    fun callingBestSelling(city: String) = viewModelScope.launch {
        repository.BestSellingProducts(city).collectLatest {
            when (it) {
                is ApiState.Success -> {
                    bestsellingProductsResponse.value = it.data
                }
                is ApiState.Failure -> {
                    bestsellingProductsResponse.value =
                        HomeAllProductsResponse(null, "Something went wrong", 401)
                }
                is ApiState.Loading -> {

                }
            }
        }
    }

    fun callingDashboardCategoryWiseList() = viewModelScope.launch {
        repository.callingDasboardProducts().collectLatest {
            when (it) {
                is ApiState.Success -> {
                    CategoryWiseDashboardResponse.value = it.data
                }
                is ApiState.Failure -> {
                    CategoryWiseDashboardResponse.value =
                        CategoryWiseDashboardResponse(null, "Something went wrong", 401)
                }
                is ApiState.Loading -> {

                }
            }
        }
    }

    fun setFilterList(filterlist: List<HomeAllProductsResponse.HomeResponse>?) {
      this.filterlist=filterlist
    }
    fun getfilterlist(): List<HomeAllProductsResponse.HomeResponse>?{
        return filterlist
    }

    val allresponse: Flow<PagingData<HomeAllProductsResponse.HomeResponse>> =
        Pager(PagingConfig(pageSize = NETWORK_PAGE_SIZE)) {
            pagingDataSource
        }.flow.cachedIn(viewModelScope)

}

class PaginSoucrce @Inject constructor(private val apiService: ApiService,val calling: CallingCategoryWiseData) :
    PagingSource<Int, HomeAllProductsResponse.HomeResponse>() {

    val INITIAL_LOAD_SIZE = 0
    override fun getRefreshKey(state: PagingState<Int, HomeAllProductsResponse.HomeResponse>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HomeAllProductsResponse.HomeResponse> {
        return try {

            val position = params.key ?: 1
            val offset =
                if (params.key != null) ((position - 1) * NETWORK_PAGE_SIZE) + 1 else INITIAL_LOAD_SIZE
            return try {
                // val jsonResponse = service.getCryptoList(start = offset, limit = params.loadSize).data
                val homeproduts = apiService.getHomeAllProducts(offset, params.loadSize,calling.gettingData())

                val nextKey = if (homeproduts.body()?.list?.isEmpty() == true) {
                    null
                } else {
                    // initial load size = 3 * NETWORK_PAGE_SIZE
                    // ensure we're not requesting duplicating items, at the 2nd request
                    position + (params.loadSize / NETWORK_PAGE_SIZE)
                }
                LoadResult.Page(
                    data = homeproduts.body()?.list ?: emptyList(),
                    prevKey = null, // Only paging forward.
                    // assume that if a full page is not loaded, that means the end of the data
                    nextKey = nextKey
                )

            } catch (exception: IOException) {
                return LoadResult.Error(exception)
            } catch (exception: HttpException) {
                return LoadResult.Error(exception)
            }
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}