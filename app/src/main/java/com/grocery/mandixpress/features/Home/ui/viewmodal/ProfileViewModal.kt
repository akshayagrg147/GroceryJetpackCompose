package com.grocery.mandixpress.features.Home.ui.viewmodal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.grocery.mandixpress.RoomDatabase.Dao
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.common.doOnFailure
import com.grocery.mandixpress.common.doOnLoading
import com.grocery.mandixpress.common.doOnSuccess
import com.grocery.mandixpress.data.modal.AllOrdersHistoryList
import com.grocery.mandixpress.data.modal.OrderStatusRequest
import com.grocery.mandixpress.data.modal.UserResponse
import com.grocery.mandixpress.data.modal.commonResponse
import com.grocery.mandixpress.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModal @Inject constructor(
    val repository: CommonRepository,
    val dao: Dao,
    val shared: sharedpreferenceCommon
) : ViewModel() {

    private val userProfileResponse: MutableStateFlow<CommonUiObjectResponse<UserResponse>> =
        MutableStateFlow(CommonUiObjectResponse())
    var responseLiveData = userProfileResponse.asStateFlow()
    private set

    private val cancelResponse = MutableLiveData<CommonUiObjectResponse<commonResponse>>()
    val cancelResponseLiveData: LiveData<CommonUiObjectResponse<commonResponse>> = cancelResponse

    private val orderhistory: MutableStateFlow<CommonUiObjectResponse<AllOrdersHistoryList>> =
        MutableStateFlow(CommonUiObjectResponse())
    var orderhistorydata = orderhistory.asStateFlow()
    private set

fun callingOrderStatus(orderRequest: OrderStatusRequest) {
    Log.d("callingOrderStatus","true")
    onEvent(ProfileEvent.cancelOrder(orderRequest))


}




    fun onEvent(event: ProfileEvent){
        when(event){
          is  ProfileEvent.OrderEvent -> viewModelScope.launch {
                repository.getAllOrders("Ordered")
                    .doOnSuccess {
                        orderhistory.value = CommonUiObjectResponse(
                            data = it
                        )
                    }
                    .doOnFailure {
                        orderhistory.value = CommonUiObjectResponse(
                            error = it?.message ?: "something went wrong.."
                        )
                    }
                    .doOnLoading {
                        orderhistory.value = CommonUiObjectResponse(
                            isLoading = true
                        )
                    }
                    .collect()
            }
            is  ProfileEvent.DeliverEvent -> viewModelScope.launch {
                repository.getAllOrders("Delivered")
                    .doOnSuccess {
                        orderhistory.value = CommonUiObjectResponse(
                            data = it
                        )
                    }
                    .doOnFailure {
                        orderhistory.value = CommonUiObjectResponse(
                            error = it?.message ?: "something went wrong.."
                        )
                    }
                    .doOnLoading {
                        orderhistory.value = CommonUiObjectResponse(
                            isLoading = true
                        )
                    }
                    .collect()
            }
            is  ProfileEvent.CancelEvent -> viewModelScope.launch {
                repository.getAllOrders("Cancelled")
                    .doOnSuccess {
                        orderhistory.value = CommonUiObjectResponse(
                            data = it
                        )
                    }
                    .doOnFailure {
                        orderhistory.value = CommonUiObjectResponse(
                            error = it?.message ?: "something went wrong.."
                        )
                    }
                    .doOnLoading {
                        orderhistory.value = CommonUiObjectResponse(
                            isLoading = true
                        )
                    }
                    .collect()
            }
          is  ProfileEvent.callingUserProfile->viewModelScope.launch {
                repository.getUserResponse(shared.getMobileNumber(),shared.getPostalCode())
                    .doOnSuccess {
                        userProfileResponse.value= CommonUiObjectResponse(data=it)
                    }
                    .doOnFailure {
                        userProfileResponse.value=CommonUiObjectResponse(error=it?.message?:"something went wrong")
                    }
                    .doOnLoading {
                        userProfileResponse.value=CommonUiObjectResponse(isLoading = true)
                    }.collect()
            }
            is  ProfileEvent.cancelOrder->viewModelScope.launch {
                repository.cancelOrder(event.data)
                    .collectLatest {
                        when(it){
                            is ApiState.Success->{
                                Log.d("callingOrderStatus","true1")
                                cancelResponse.value= CommonUiObjectResponse(data=it.data)

                            }
                            is ApiState.Failure->{
                                Log.d("callingOrderStatus","true2")
                                cancelResponse.value=CommonUiObjectResponse(error=it.msg.message?:"something went wrong")
                            }
                            is ApiState.Loading->{
                                Log.d("callingOrderStatus","true3")
                                cancelResponse.value=CommonUiObjectResponse(isLoading = true)
                            }
                        }
                    }

            }

        }
    }

    fun clearSharedPreference() :Boolean{
        return shared.clearSharePreference()


    }

    fun getDeliveryBoyNumber(): String {
        return shared.getDeliveryContactNumber()

    }



}

sealed class ProfileEvent{
    data class OrderEvent( val str:String) : ProfileEvent()
    data class DeliverEvent ( val str:String): ProfileEvent()
    data class CancelEvent( val str:String) : ProfileEvent()
    data class callingUserProfile( val str:String):ProfileEvent()
    data class cancelOrder( val data: OrderStatusRequest):ProfileEvent()

}


data class CommonUiObjectResponse<T>(
    val data:T? = null,
    val error:String = "",
    val isLoading:Boolean = false
)

