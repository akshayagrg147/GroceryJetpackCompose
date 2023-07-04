package com.grocery.groceryapp.data.modal


import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.RawValue
@Parcelize
@JsonClass(generateAdapter = true)
data class HomeAllProductsResponse(
    @Json(name = "itemData")
    var list: @RawValue List<HomeResponse>?= emptyList(),
    @Json(name = "message")
    val message: String?=null,
    @Json(name = "statusCode")
    val statusCode: Int?=0
): Parcelable {
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class HomeResponse(
        @Json(name = "price")
        val price: String?=null,
        @Json(name = "ProductImage1")
        val productImage1: String?=null,
        @Json(name = "productName")
        val productName: String?=null,
        @Json(name = "quantity")
        val quantity: String?=null,
        @Json(name = "productId")
        val ProductId: String?=null,
        @Json(name="actual_price")
        val orignalprice: String?=null

    ):Parcelable
}