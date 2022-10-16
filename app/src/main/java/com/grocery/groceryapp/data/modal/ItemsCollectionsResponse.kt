package com.grocery.groceryapp.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemsCollectionsResponse(
    @Json(name = "list")
    val list: List<SubItems>?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "statusCode")
    val statusCode: Int?
) {
    @JsonClass(generateAdapter = true)
    data class SubItems (
        @Json(name = "actual_price")
        val actualPrice: String?,
        @Json(name = "itemCategoryId")
        val itemCategoryId: String?,
        @Json(name = "price")
        val price: String?,
        @Json(name = "ProductDescription")
        val productDescription: String?,
        @Json(name = "productId")
        val productId: String?,
        @Json(name = "ProductImage1")
        val productImage1: String?,
        @Json(name = "ProductImage2")
        val productImage2: String?,
        @Json(name = "ProductImage3")
        val productImage3: String?,
        @Json(name = "productName")
        val productName: String?="",
        @Json(name = "quantity")
        val quantity: String?
    )
}