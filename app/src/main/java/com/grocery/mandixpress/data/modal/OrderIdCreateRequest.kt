package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class OrderIdCreateRequest(
    @Json(name = "address")
    val address: String?,
    @Json(name = "mobilenumber")
    val mobilenumber: String?,
    @Json(name = "orderList")
    val orderList: List<Order?>?,
    @Json(name = "paymentmode")
    val paymentmode: String?,
    @Json(name = "totalOrderValue")
    val totalOrderValue: String?,
    @Json(name = "pincode")
    var pincode: String?,
    @Json(name = "fcm_token")
    var fcm_token: String?=null,

) {
    @JsonClass(generateAdapter = true)
    data class Order(
        @Json(name = "productId")
        val productId: String?,
        @Json(name = "productName")
        val productName: String?,
        @Json(name = "productprice")
        val productprice: String?,
        @Json(name = "quantity")
        val quantity: String?
    )
}