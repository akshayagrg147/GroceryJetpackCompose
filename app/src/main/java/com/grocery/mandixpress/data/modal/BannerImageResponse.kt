package com.grocery.mandixpress.data.modal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class BannerImageResponse(
    val itemData: List<ItemData>,
    val message: String,
    val statusCode: Int=400
):Parcelable {
    @Parcelize
    data class ItemData(
        val bannercategory1: String?=null,
        val bannercategory2: String?=null,
        val bannercategory3: String?=null,
        val imageUrl1: String?=null,
        val imageUrl2: String?=null,
        val imageUrl3: String?=null,
        val subCategoryList: List<SubCategory>?= emptyList()
    ):Parcelable {
        @Parcelize
        data class SubCategory(
            val name: String,
            val subCategoryUrl: String
        ):Parcelable
    }
}