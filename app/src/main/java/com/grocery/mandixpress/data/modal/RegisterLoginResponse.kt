package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterLoginResponse(
    @Json(name = "message")
    val message: String?=null,
    @Json(name = "statusCode")
    val statusCode: Int?=0,
    @Json(name = "token")
    val token: String?=null,
    @Json(name = "status")
    val status: Boolean?=false
)