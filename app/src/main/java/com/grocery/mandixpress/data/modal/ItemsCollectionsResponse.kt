package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ItemsCollectionsResponse(
    @Json(name = "itemData")
    val list: List<SubItems>? = emptyList(),
    @Json(name = "message")
    val message: String?,
    @Json(name = "statusCode")
    val statusCode: Int?
) {
    @JsonClass(generateAdapter = true)
    data class SubItems(
        @Json(name = "orignal_price")
        val orignal_price: String = "",
        @Json(name = "item_category_name")
        val item_category_name: String = "",
        @Json(name = "selling_price")
        val selling_price: String = "",
        @Json(name = "productDescription")
        val productDescription: String = "",
        @Json(name = "productId")
        val productId: String = "",
        @Json(name = "productImage1")
        val productImage1: String = "",
        @Json(name = "productImage2")
        val productImage2: String = "",
        @Json(name = "productImage3")
        val productImage3: String = "",
        @Json(name = "productName")
        val productName: String = "",
        @Json(name = "quantity")
        val quantity: String = "",
        @Json(name = "quantityInstructionController")
        val quantityInstructionController: String = ""

    )
}