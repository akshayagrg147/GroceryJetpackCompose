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
import com.google.gson.Gson
import com.grocery.groceryapp.RoomDatabase.Dao
import com.grocery.groceryapp.SharedPreference.sharedpreferenceCommon
import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.data.modal.FetchCart
import com.grocery.groceryapp.data.modal.HomeAllProductsResponse
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class HomeAllProductsViewModal @Inject constructor(val repository: CommonRepository,val
  sharedPreferences: sharedpreferenceCommon,val dao: Dao,@ApplicationContext context: Context
):ViewModel(){
    var passingdata:MutableLiveData<List<HomeAllProductsResponse.HomeResponse>> = MutableLiveData()
    public val homeAllProductsResponse:MutableState<HomeAllProductsResponse> = mutableStateOf(HomeAllProductsResponse(null,null,null))
    private val exclusiveProductsResponse:MutableState<HomeAllProductsResponse> = mutableStateOf(HomeAllProductsResponse(null,null,null))

    private val bestsellingProductsResponse:MutableState<HomeAllProductsResponse> = mutableStateOf(HomeAllProductsResponse(null,null,null))
    val homeAllProductsResponse1: State<HomeAllProductsResponse> = homeAllProductsResponse
    val exclusiveProductsResponse1: State<HomeAllProductsResponse> = exclusiveProductsResponse
    val bestsellingProductsResponse1: State<HomeAllProductsResponse> = bestsellingProductsResponse

    private val addresslist:MutableState<List<AddressItems>> = mutableStateOf(emptyList())
    val list:State<List<AddressItems>> =addresslist

    private val updatecount:MutableState<FetchCart> =mutableStateOf(FetchCart())
    val getitemcount:MutableState<FetchCart> =updatecount




fun gettingAddres():String{
    return sharedPreferences.getCombinedAddress()
}
    suspend fun getAddress()= withContext(Dispatchers.IO) {
        dao.getAllAddress().collectLatest {
            addresslist.value=it
            Log.d("addessget", Gson().toJson(it.size))
        }


    }
    fun deleteAddress(id:Int)=viewModelScope.launch(Dispatchers.IO){
        dao.deleteAddress(id.toString())

    }
   suspend fun getCartItem(context: Context)= withContext(Dispatchers.IO){
       var totalcount: Int =
           dao.getTotalProductItems()
       var totalPrice: Int =
           dao.getTotalProductItemsPrice()
       updatecount.value.totalcount=totalcount
       updatecount.value.totalprice=totalPrice

    }

    fun callingHomeAllProducts()=viewModelScope.launch {
        repository.HomeAllProducts().collectLatest {
            when(it){
                is ApiState.Success->{
                    Log.d("listofdata", "home")
                    homeAllProductsResponse.value=it.data
                }
                is ApiState.Failure->{
                    homeAllProductsResponse.value=HomeAllProductsResponse(null,it.msg.message,401)
                }
                is ApiState.Loading ->{

                }
            }
        }
    }
    fun callingExcusiveProducts()=viewModelScope.launch {
        repository.ExclusiveProducts().collectLatest {
            when(it){
                is ApiState.Success->{
                    Log.d("listofdata", "exclsuive")
                    exclusiveProductsResponse.value=it.data
                }
                is ApiState.Failure->{
                    Log.d("listofdata", "exclusivefail")
                    exclusiveProductsResponse.value=HomeAllProductsResponse(null,"Something went wrong",401)
                }
                is ApiState.Loading ->{

                }
            }
        }
    }
    fun gettingData():LiveData<List<HomeAllProductsResponse.HomeResponse>>{
        return passingdata

    }
    fun passingData(list: List<HomeAllProductsResponse.HomeResponse>){
        passingdata.value=list

    }
    fun callingBestSelling()=viewModelScope.launch {
        repository.BestSellingProducts().collectLatest {
            when(it){
                is ApiState.Success->{
                    Log.d("listofdata", "best")
                    bestsellingProductsResponse.value=it.data
                }
                is ApiState.Failure->{
                    Log.d("listofdata", "bestfail${it.msg}")
                    bestsellingProductsResponse.value=HomeAllProductsResponse(null,"Something went wrong",401)
                }
                is ApiState.Loading ->{

                }
            }
        }
    }

}