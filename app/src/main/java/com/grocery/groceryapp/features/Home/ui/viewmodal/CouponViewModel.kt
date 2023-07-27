package com.grocery.groceryapp.features.Home.ui.viewmodal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grocery.groceryapp.features.Home.domain.modal.Coupon

import androidx.compose.runtime.*

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.lifecycle.viewmodel.compose.viewModel

class CouponViewModel : ViewModel() {
    // Simulated list of coupons (you can replace this with your actual data source)
    private val _coupons = MutableStateFlow<List<Coupon>>(emptyList())
    val coupons: StateFlow<List<Coupon>> get() = _coupons.asStateFlow()

    init {
        // Fetch the coupons from your data source and update the StateFlow
        _coupons.value = getCoupons()
    }

    // Sample function to provide a list of coupons
    private fun getCoupons(): List<Coupon> {
        return listOf(
            Coupon(1, "50% Off on All Items", "Use coupon code 'HALFOFF' for a 50% discount on all items.",50,"HALF50"),
            Coupon(2, "20% Off on Electronics", "Get 20% off on all electronic products.",20,"HALF20"),
            Coupon(3, "Free Shipping", "Enjoy free shipping on orders over $50.",10,"HALF10"),
            // Add more dummy coupons here...
        )
    }
}
