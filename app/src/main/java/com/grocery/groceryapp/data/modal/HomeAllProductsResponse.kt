package com.grocery.groceryapp.data.modal


import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.RawValue
@Parcelize
@JsonClass(generateAdapter = true)
data class HomeAllProductsResponse(
    @Json(name = "list")
    val list: @RawValue List<HomeResponse>?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "statusCode")
    val statusCode: Int?
): Parcelable {
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class HomeResponse(
        @Json(name = "price")
        val price: String?,
        @Json(name = "ProductImage1")
        val productImage1: String?=null,
        @Json(name = "productName")
        val productName: String?,
        @Json(name = "quantity")
        val quantity: String?,
        @Json(name = "productId")
        val ProductId: String?,
        @Json(name="actual_price")
        val orignalprice: String?

    ):Parcelable
}