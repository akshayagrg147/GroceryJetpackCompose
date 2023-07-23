package com.grocery.groceryapp.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductByIdResponseModal(
    @Json(name = "ProductResponse")
    val homeproducts: ProductResponse?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "statusCode")
    val statusCode: Int?
) {
    @JsonClass(generateAdapter = true)
    data class ProductResponse(

        @Json(name="selling_price")
    val selling_price: String?,
        @Json(name="orignal_price")
        val orignalprice: String?,

        @Json(name = "productId")
        val productId: String?=null,
        @Json(name = "ProductImage1")
        val productImage1: String?=null,
        @Json(name = "ProductImage2")
        val productImage2: String?=null,
        @Json(name = "ProductImage3")
        val productImage3: String?=null,
        @Json(name = "productName")
        val productName: String?=null,
        @Json(name = "quantity")
        val quantity: String?=null,
        @Json(name = "ProductDescription")
        val ProductDescription:String?=null,
        @Json(name = "rating")
    val rating: List<Rating?>?
    )
    @JsonClass(generateAdapter = true)
    data class Rating(
        @Json(name = "customerId")
        val customerId: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "rating")
        val rating: String?,
        @Json(name = "remark")
        val remark: String?
    )
}