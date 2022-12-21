package com.grocery.groceryapp.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterLoginRequest(
    @Json(name = "email")
    val email: String?=null,
    @Json(name = "name")
    val name: String?=null,
    @Json(name = "phone")
    val phone: String?=null
)
@JsonClass(generateAdapter = true)
data class ExclusiveOfferRequest(
    @Json(name = "city")
    val city: String?=null,

)