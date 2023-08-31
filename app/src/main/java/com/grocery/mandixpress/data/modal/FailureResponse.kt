package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FailureResponse(
    @Json(name = "statusCode")
    val statusCode: String?=null,
    @Json(name = "message")
    val message: String?=null,

)