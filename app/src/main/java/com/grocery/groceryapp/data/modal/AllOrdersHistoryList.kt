package com.grocery.groceryapp.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AllOrdersHistoryList(
    @Json(name = "list")
    val list: List<Order>?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "statusCode")
    val statusCode: Int?
) {
    @JsonClass(generateAdapter = true)
    data class Order (
        @Json(name = "address")
        val address: String?=null,
        @Json(name = "createdDate")
        val createdDate: String?=null,
        @Json(name = "mobilenumber")
        val mobilenumber: String?=null,
        @Json(name = "orderId")
        val orderId: String?=null,
        @Json(name = "orderList")
        val orderList: List<Order?>?=null,
        @Json(name = "paymentmode")
        val paymentmode: String?=null,
        @Json(name = "totalOrderValue")
        val totalOrderValue: String?=null
    ) {
        @JsonClass(generateAdapter = true)
        data class Order(
            @Json(name = "productId")
            val productId: String?,
            @Json(name = "product_name")
            val productName: String?,
            @Json(name = "productprice")
            val productprice: String?,
            @Json(name = "quantity")
            val quantity: String?
        )
    }
}