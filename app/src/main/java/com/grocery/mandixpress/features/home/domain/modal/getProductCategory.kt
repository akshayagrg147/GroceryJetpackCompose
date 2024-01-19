package com.grocery.mandixpress.features.home.domain.modal

import android.os.Parcelable
import com.grocery.mandixpress.data.modal.AdminResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class getProductCategory(
    var itemData: List<ItemData>? = emptyList(),
    val message: String? = null,
    val statusCode: Int? = null,

    ) :Parcelable{
    @Parcelize
    data class ItemData(
        val category: String? = null,
        val imageUrl: String?=null,
        val subCategoryList: List<SubCategory>? = null,
        val sellerCategoryData: SellerCategoryData ?=null
    ) : Parcelable{
        @Parcelize
        data class SubCategory(
            val name: String,
            val subCategoryUrl: String?=null
        ):Parcelable
        @Parcelize
        data class SellerCategoryData(var sellerCatergoryList:List<CategoryImage?>):Parcelable
        @Parcelize
        data class CategoryImage(var name: String?,var image: String, var sellerSubCatergoryList:List<String?>):Parcelable
    }
}