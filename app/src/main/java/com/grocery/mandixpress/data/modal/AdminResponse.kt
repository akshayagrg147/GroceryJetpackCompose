package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class AdminResponse(
    val itemData: List<ItemData>,
    val message: String,
    val statusCode: Int
) {
    data class ItemData(
        val pincode: String,
        val price: String
    )
}