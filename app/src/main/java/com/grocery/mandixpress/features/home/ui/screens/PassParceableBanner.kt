package com.grocery.mandixpress.features.home.ui.screens

import android.os.Parcelable
import com.grocery.mandixpress.data.modal.BannerImageResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class PassParceableBanner(val index: Int?=-1, val second: BannerImageResponse.ItemData?=BannerImageResponse.ItemData()):Parcelable
