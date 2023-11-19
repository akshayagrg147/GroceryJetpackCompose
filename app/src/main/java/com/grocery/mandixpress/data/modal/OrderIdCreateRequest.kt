package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class OrderIdCreateRequest(
    @Json(name = "address") val address: String?=null,
    @Json(name = "mobilenumber") val mobilenumber: String?=null,
    @Json(name = "orderList") val orderList: List<Order>?=null,
    @Json(name = "paymentmode") val paymentmode: String?=null,
    @Json(name = "totalOrderValue") val totalOrderValue: String?=null,
    @Json(name = "pincode") var pincode: String?=null,
    @Json(name = "fcm_token") var fcm_token: String?=null,
    @Json(name = "listOfSellerId") val listOfSellerId: List<String>?=null,
    @Json(name = "fcm_tokenSeller") val fcm_tokenSeller: String?=null
){@JsonClass(generateAdapter = true)
    data class Order(
        @Json(name = "productId") val productId: String?=null,
        @Json(name = "productName") val productName: String?=null,
        @Json(name = "productprice") val productpricestrProductPrice: String?=null,
        @Json(name = "quantity") val quantity: String?=null,
        @Json(name = "sellerIdName") val sellerIdName: String?=null
    )

}



