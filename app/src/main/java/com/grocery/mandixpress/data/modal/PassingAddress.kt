package com.grocery.mandixpress.data.modal

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PassingAddress(
    val id: Long? = 0,
    val name: String? = null,
    val email: String? = null,
    val phone_number: String? = null,
    val pincode: String? = null,
    val landmark: String? = null,
    val address1: String? = null,
    val address2: String? = null,
) : Parcelable
