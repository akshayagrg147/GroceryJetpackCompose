package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CheckNumberExistResponse(
    @Json(name = "isMobileExist")
    val isMobileExist: Boolean?,
    @Json(name = "JwtToken")
    val jwtToken: String?,
    @Json(name = "status")
    val status: Boolean?,
    @Json(name = "statusCode")
    val statusCode: Int?
)