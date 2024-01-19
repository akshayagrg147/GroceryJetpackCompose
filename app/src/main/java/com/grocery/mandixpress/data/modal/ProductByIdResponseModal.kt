package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductByIdResponseModal(
    @Json(name = "ProductResponse")
    val homeproducts: ProductResponse?=null,
    @Json(name = "message")
    val message: String?=null,
    @Json(name = "statusCode")
    val statusCode: Int?=401
) {
    @JsonClass(generateAdapter = true)
    data class ProductResponse(

        @Json(name = "selling_price")
        val selling_price: String?=null,
        @Json(name = "orignal_price")
        val orignalprice: String?=null,

        @Json(name = "productId")
        val productId: String? = null,
        @Json(name = "productImage1")
        val productImage1: String? = null,
        @Json(name = "productImage2")
        val productImage2: String? = null,
        @Json(name = "productImage3")
        val productImage3: String? = null,
        @Json(name = "productName")
        val productName: String? = null,
        @Json(name = "quantity")
        val quantity: String? = null,
        @Json(name = "productDescription")
        val ProductDescription: String? = null,
        @Json(name = "rating")
        val rating: List<Rating?>?,
        @Json(name = "sellerId")
        var sellerId:String?=null
    )

    @JsonClass(generateAdapter = true)
    data class Rating(
        @Json(name = "customerId")
        val customerId: String?=null,
        @Json(name = "name")
        val name: String?=null,
        @Json(name = "rating")
        val rating: String?=null,
        @Json(name = "remark")
        val remark: String?=null
    )
}