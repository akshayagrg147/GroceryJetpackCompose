package com.grocery.groceryapp.features.Home.domain.modal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class getProductCategory(
    var itemData: List<ItemData>? = null,
    val message: String? = null,
    val statusCode: Int? = null
) :Parcelable{
    @Parcelize
    data class ItemData(
        val category: String? = null,
        val imageUrl: String?=null,
        val subCategoryList: List<SubCategory>? = null
    ) : Parcelable{
        @Parcelize
        data class SubCategory(
            val name: String,
            val subCategoryUrl: String?=null
        ):Parcelable
    }
}