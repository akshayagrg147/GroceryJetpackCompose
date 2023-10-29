package com.grocery.mandixpress.features.home.ui.viewmodal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grocery.mandixpress.SharedPreference.sharedpreferenceCommon
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.features.home.domain.modal.CouponResponse
import com.grocery.mandixpress.features.splash.domain.repository.CommonRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor(val repository: CommonRepository,val sharedpreferenceCommon: sharedpreferenceCommon) : ViewModel() {
    // Simulated list of coupons (you can replace this with your actual data source)


    private val _coupons:MutableStateFlow<ComposeUiResponse<CouponResponse>> = MutableStateFlow(
        ComposeUiResponse()
    )

    var coupons = _coupons.asStateFlow()
        private set


    // Sample function to provide a list of coupons

    fun onEvents(events: CouponEvents) {
        when(events){
            is CouponEvents.CouponEvent->{
                viewModelScope.launch {
                    repository.getAppCoupons(sharedpreferenceCommon.getPostalCode()).collectLatest {
                        when(it){
                            is ApiState.Success->{
                                _coupons.value = ComposeUiResponse(data = it.data)


                            }
                            is ApiState.Failure->{
                                _coupons.value = ComposeUiResponse( error = it?.msg.toString())
                            }
                            is ApiState.Loading->{
                                _coupons.value= ComposeUiResponse( isLoading = true)
                            }

                        }
                    }
                }
            }
        }
    }

}
sealed class CouponEvents{
     class CouponEvent() : CouponEvents()
}

