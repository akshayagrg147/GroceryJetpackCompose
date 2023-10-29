package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProductIdIdModal(
    @Json(name = "ProductId")
    val productId: String? = null,
    @Json(name = "pincode")
    val pincode: String? = null
)