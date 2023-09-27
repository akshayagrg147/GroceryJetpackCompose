package com.grocery.mandixpress.features.Home.ui.viewmodal

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
import com.grocery.mandixpress.data.modal.UserResponse
import com.grocery.mandixpress.features.Spash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    private val orderhistory: MutableStateFlow<CommonUiObjectResponse<AllOrdersHistoryList>> =
        MutableStateFlow(CommonUiObjectResponse())
    var orderhistorydata = orderhistory.asStateFlow()
    private set






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
              Log.d("profileResponse","called1")
                repository.getUserResponse(shared.getMobileNumber(),shared.getPostalCode())
                    .doOnSuccess {
                        Log.d("profileResponse","called11")
                        userProfileResponse.value= CommonUiObjectResponse(data=it)
                    }
                    .doOnFailure {
                        Log.d("profileResponse","called1 ${it?.message}")
                        userProfileResponse.value=CommonUiObjectResponse(error=it?.message?:"something went wrong")
                    }
                    .doOnLoading {
                        Log.d("profileResponse","called1 loading")
                        userProfileResponse.value=CommonUiObjectResponse(isLoading = true)
                    }.collect()
            }

        }
    }

    fun clearSharedPreference() {
        shared.clearSharePreference()

    }


}

sealed class ProfileEvent{
    data class OrderEvent( val str:String) : ProfileEvent()
    data class DeliverEvent ( val str:String): ProfileEvent()
    data class CancelEvent( val str:String) : ProfileEvent()
    data class callingUserProfile( val str:String):ProfileEvent()

}


data class CommonUiObjectResponse<T>(
    val data:T? = null,
    val error:String = "",
    val isLoading:Boolean = false
)

data class CommonUiListResponse<T>(
    val data:List<T> = emptyList(),
    val error:String = "",
    val isLoading:Boolean = false
)