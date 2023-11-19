package com.grocery.mandixpress.data.modal


import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.RawValue

@Parcelize
@JsonClass(generateAdapter = true)
data class HomeAllProductsResponse(
    @Json(name = "itemData")
    var list: @RawValue List<HomeResponse>? = emptyList(),
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "statusCode")
    val statusCode: Int? = 0
) : Parcelable {
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class HomeResponse(
        @Json(name = "selling_price")
        val selling_price: String? = null,
        @Json(name = "productImage1")
        val productImage1: String? = null,
        @Json(name = "productImage2")
        val productImage2: String? = null,
        @Json(name = "productImage3")
        val productImage3: String? = null,
        @Json(name = "productName")
        val productName: String? = null,
        @Json(name = "productDescription")
        val productDescription: String? = null,


        @Json(name = "quantity")
        val quantity: String? = null,
        @Json(name = "quantityInstructionController")
        val quantityInstructionController: String? = null,

        @Json(name = "productId")
        val ProductId: String? = null,
        @Json(name = "orignal_price")
        val orignal_price: String? = null,
        @Json(name = "sellerId")
        var sellerId:String?=null

    ) : Parcelable
}