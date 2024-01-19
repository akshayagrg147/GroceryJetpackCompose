package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "message")
    val message: String?=null,
    @Json(name = "name")
    val name: Name?=null,
    @Json(name = "statusCode")
    val statusCode: Int?=0,
    @Json(name = "profileImage")
    val profileImage: Int?=0
) {
    @JsonClass(generateAdapter = true)
    data class Name(
        @Json(name = "email")
        val email: String?=null,
        @Json(name = "name")
        val name: String?=null,
        @Json(name = "phone")
        val phone: String?=null,
        @Json(name = "userId")
        val userId: String?=null,
        @Json(name = "order")
        val order: String?=null,
        @Json(name = "cancel")
        val cancel: String?=null,
        @Json(name = "deliver")
        val deliver: String?=null
    )
}