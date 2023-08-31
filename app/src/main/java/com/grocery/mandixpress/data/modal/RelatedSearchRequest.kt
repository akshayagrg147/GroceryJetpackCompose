package com.grocery.mandixpress.data.modal

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RelatedSearchRequest (
    @Json(name = "Price")
    val Price:String?=null,
    @Json(name = "category")
    val category:String?=null)