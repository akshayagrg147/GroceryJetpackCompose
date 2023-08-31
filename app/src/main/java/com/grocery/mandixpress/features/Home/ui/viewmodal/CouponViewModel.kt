package com.grocery.mandixpress.features.Home.ui.viewmodal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.common.api.Api
import com.grocery.mandixpress.common.ApiState
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import com.grocery.mandixpress.data.modal.ProductIdIdModal
import com.grocery.mandixpress.data.modal.RelatedSearchRequest
import com.grocery.mandixpress.features.Home.domain.modal.Coupon
import com.grocery.mandixpress.features.Home.domain.modal.CouponResponse
import com.grocery.mandixpress.features.Spash.domain.repository.CommonRepository
import com.grocery.mandixpress.features.Spash.ui.viewmodel.ComposeUiResponse
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CouponViewModel @Inject constructor(val repository: CommonRepository,) : ViewModel() {
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
                    repository.getAppCoupons().collectLatest {
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

