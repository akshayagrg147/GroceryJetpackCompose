package com.grocery.mandixpress.features.Home.ui.screens

import android.os.Parcelable
import com.grocery.mandixpress.data.modal.BannerImageResponse
import com.grocery.mandixpress.data.modal.HomeAllProductsResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class PassParceableArrayList(val list: List<HomeAllProductsResponse.HomeResponse>):Parcelable
