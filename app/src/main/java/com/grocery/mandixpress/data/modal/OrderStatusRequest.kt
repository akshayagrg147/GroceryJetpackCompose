package com.grocery.mandixpress.data.modal

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class OrderStatusRequest(
    val address: String,
    val changeTime: Long,
    val createdDate: String,
    val fcm_token: String,
    val mobilenumber: String,
    val orderId: String,
    val orderList: List<Orders>,
    val orderStatus: String,
    val paymentmode: String,
    val pincode: Any,
    val totalOrderValue: String
) {
    data class Orders(
        val productId: String,
        val productName: String,
        val productprice: String,
        val quantity: String
    )
}