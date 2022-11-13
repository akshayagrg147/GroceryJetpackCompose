package com.grocery.groceryapp.data.modal


import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class AllOrdersHistoryList(
    @Json(name = "list")
    val list: List<Orders>?= emptyList(),
    @Json(name = "message")
    val message: String?=null,
    @Json(name = "statusCode")
    val statusCode: Int?=0
):Parcelable {
    @Parcelize
    @JsonClass(generateAdapter = true)
    data class Orders(
        @Json(name = "address")
        val address: String?,
        @Json(name = "createdDate")
        val createdDate: String?,
        @Json(name = "mobilenumber")
        val mobilenumber: String?,
        @Json(name = "orderId")
        val orderId: String?,
        @Json(name = "orderList")
        val orderList: List<Order?>?,
        @Json(name = "paymentmode")
        val paymentmode: String?,
        @Json(name = "totalOrderValue")
        val totalOrderValue: String?
    ):Parcelable {
        @Parcelize
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
        ):Parcelable
    }
}