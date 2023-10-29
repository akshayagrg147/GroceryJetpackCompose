package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RegisterLoginRequest(
    @Json(name = "email")
    val email: String? = null,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "phone")
    val phone: String? = null,
    @Json(name = "pincode")
    val pincode: String? = null,
    @Json(name = "fcmtoken")
    val fcmtoken: String? = null,
    @Json(name = "changetime")
    val changetime: String? = null,

    )

@JsonClass(generateAdapter = true)
data class ExclusiveOfferRequest(
    @Json(name = "city")
    val city: String? = null,
    @Json(name = "pincode")
    val pincode: String? = null,

    )