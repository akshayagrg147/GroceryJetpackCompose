package com.grocery.groceryapp.features.Spash.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.grocery.groceryapp.RoomDatabase.CartItems
import com.grocery.groceryapp.RoomDatabase.Dao
import com.grocery.groceryapp.RoomDatabase.TodoRepository
import com.grocery.groceryapp.common.ApiState
import com.grocery.groceryapp.data.modal.*
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import com.grocery.groceryapp.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartItemsViewModal @Inject constructor(val dao: Dao, val repository: CommonRepository,val repo:TodoRepository) :
    ViewModel() {

init {
    getAllCartAddressItems()
}



    private val allcartitems: MutableState<List<CartItems>> = mutableStateOf(emptyList())
    val allcartitemsState: State<List<CartItems>> = allcartitems

    private val SavingAmmountMutable: MutableState<Int> = mutableStateOf(0)
    val SavingAmountState: State<Int> = SavingAmmountMutable

    private val itemcollections: MutableState<ItemsCollectionsResponse> =
        mutableStateOf(ItemsCollectionsResponse(null, null, null))
    val itemcollections1: State<ItemsCollectionsResponse> = itemcollections

    private val updatecount: MutableState<FetchCart> = mutableStateOf(FetchCart())
    val getitemcount: MutableState<FetchCart> = updatecount

    private val totalcount: MutableState<Int> =
        mutableStateOf(0)
    val totalcountState: State<Int> = totalcount
    private val totalPrice: MutableState<Int> =
        mutableStateOf(0)
    val totalPriceState: State<Int> = totalPrice



    private val orderConfirmedStatus: MutableState<OrderIdResponse> =
        mutableStateOf(OrderIdResponse())
    val orderConfirmedStatusState: State<OrderIdResponse> = orderConfirmedStatus

    private val CartCountPrice: FetchCart = FetchCart()


    private var productIdCountMutable: MutableState<Int> = mutableStateOf(-1)
    val productIdCountState: State<Int> = productIdCountMutable

    private val addresslist: MutableState<List<AddressItems>> = mutableStateOf(emptyList())
    val addresslistState: State<List<AddressItems>> = addresslist

    private fun getAllCartAddressItems() = viewModelScope.launch {

        repo.getCartItems().catch { e->  Log.d("main", "Exception: ${e.message} ") }.collect{
            allcartitems.value=it
        }
        repo. getAddressItems().catch { e->  Log.d("main", "Exception: ${e.message} ") }.collect{
            addresslist.value=it
        }

    }

    fun deleteCartItems(productIdNumber: String?) = viewModelScope.launch(Dispatchers.IO) {

        var intger: Int =
            dao.getProductBasedIdCount(productIdNumber).first()?:0
        intger -= 1

        if (intger >= 1) {
            dao
                .updateCartItem(intger, productIdNumber!!)
        } else if (intger == 0) {
            dao
                .deleteCartItem(productIdNumber)

        }
    }
    suspend fun getCartItem(context: Context) = withContext(Dispatchers.IO) {

        var totalcount: Int =
            dao.getTotalProductItems()?.first()?:0
        var totalPrice: Int =
            dao.getTotalProductItemsPrice()?.first()?:0
        updatecount.value.totalcount = totalcount
        updatecount.value.totalprice = totalPrice



    }
     fun getSavingAmount()  {
         viewModelScope.launch {
             var totalcount1: Int =0
             withContext(Dispatchers.IO){

                 totalcount1=   dao.getTotalSavingAmount()?.first()?:0
             }
             SavingAmmountMutable.value = totalcount1
         }





    }
    fun getItemBaseOnProductId(value: String?): String {
        viewModelScope.launch() {

            var intger: Int = 0

            withContext(Dispatchers.IO) {
                 intger = dao.getProductBasedIdCount(value).first() ?:0
            }
            productIdCountMutable.value = intger

        }
        return  productIdCountMutable.value.toString()

    }

     fun getAddress() {
         viewModelScope.launch() {
             var list= emptyList<AddressItems>()
             withContext(Dispatchers.IO) {
                 list= dao.getAllAddress().first()
             }
             addresslist.value = list
         }

    }

    fun insertCartItem(
        productIdNumber: String,
        thumb: String,
        price: Int,
        productname: String,
        actualprice: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        val intger: Int = dao.getProductBasedIdCount(productIdNumber).first()?:0
        if (intger == 0) {
            val data= CartItems(
                productIdNumber,
                thumb, intger + 1,
                price, productname, actualprice, savingAmount = (actualprice.toInt()-price.toInt()).toString()
            )
            repo.insert(data)



        } else if (intger >= 1) {
            dao.updateCartItem(intger + 1, productIdNumber)

        }


    }


    fun getCartPrice() {
        viewModelScope.launch() {

            var totalcount1: Int = 0
            var totalPrice1: Int = 0
            withContext(Dispatchers.IO) {
                totalcount1 = dao.getTotalProductItems()?.first()?:0
                totalPrice1 = dao.getTotalProductItemsPrice()?.first()?:0
            }

            totalcount.value = totalcount1
            totalPrice.value=totalPrice1

        }


    }


//     fun getcartItems() {
//
//         viewModelScope.launch() {
//             var list  = emptyList<CartItems>()
//             withContext(Dispatchers.IO) {
//
//                 list=  dao.getAllCartItems().first()
//             }
//             allcartitems.value = list
//
//         }
//     }

    fun DeleteProduct(productIdNumber: String?) = viewModelScope.launch(Dispatchers.IO) {
        dao.deleteCartItem(productIdNumber)
    }

    fun calllingItemsCollectionsId(productIdIdModal: ProductIdIdModal) = viewModelScope.launch {
        repository.ItemsCollections(productIdIdModal).collectLatest {
            when (it) {
                is ApiState.Success -> {
                    itemcollections.value = it.data

                }
                is ApiState.Failure -> {
                    itemcollections.value = ItemsCollectionsResponse(null, it.msg.message, 401)

                }
                is ApiState.Loading -> {

                }

            }
        }

    }

    fun calllingBookingOrder(productIdIdModal: OrderIdCreateRequest) = viewModelScope.launch {
        repository.OrderIdRequest(productIdIdModal).collectLatest {
            when (it) {
                is ApiState.Success -> {
                    orderConfirmedStatus.value = it.data


                }
                is ApiState.Failure -> {
                    orderConfirmedStatus.value =
                        OrderIdResponse(message = it.msg.message?:"Order Failed", statusCode = 401)


                }
                is ApiState.Loading -> {

                }

            }
        }

    }


}