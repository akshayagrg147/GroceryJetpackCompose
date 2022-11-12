package com.wajahatkarim3.compose.books.ui.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PassingAddress(
    val id:Long?=0,
    val name: String?=null,
    val email: String ?=null,
    val phone_number: String?=null,
    val pincode: String? =null,
    val landmark: String? =null,
    val address1: String? =null,
    val address2: String? =null,
): Parcelable
