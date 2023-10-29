package com.grocery.mandixpress.features.home.domain.modal

data class CouponResponse(
    val itemData: List<ItemData>,
    val message: String,
    val statusCode: Int
) {
    data class ItemData(
        val couponCode: String,
        val couponTitle: String,
        val discountPercentage: String,
        val discountedAmount: String,
        val expireDate: String,
        val minimumPurchase: String,
        val startDate: String
    )
}