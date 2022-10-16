package com.grocery.groceryapp.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserResponse(
    @Json(name = "message")
    val message: String?,
    @Json(name = "name")
    val name: Name?,
    @Json(name = "statusCode")
    val statusCode: Int?,
    @Json(name = "profileImage")
    val profileImage: Int?
) {
    @JsonClass(generateAdapter = true)
    data class Name(
        @Json(name = "email")
        val email: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "phone")
        val phone: String?,
        @Json(name = "userId")
        val userId: String?
    )
}