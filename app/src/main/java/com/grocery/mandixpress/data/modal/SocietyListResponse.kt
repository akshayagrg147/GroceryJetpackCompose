package com.grocery.mandixpress.data.modal


import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.RawValue

@Parcelize
@JsonClass(generateAdapter = true)
data class SocietyListResponse(
    @Json(name = "itemData")
    var list: @RawValue List<Society>? = emptyList(),
    @Json(name = "message")
    val message: String? = null,
    @Json(name = "statusCode")
    val statusCode: Int? = 0
) : Parcelable {
    @JsonClass(generateAdapter = true)
    @Parcelize
    data class Society(
        @Json(name = "name")
        val name: String? = null,
        @Json(name = "id")
        val id: Int? = -1,


    ) : Parcelable
}