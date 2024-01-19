package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class AdminResponse(
    val itemData: List<ItemData>,
    val message: String,
    val statusCode: Int
) {
    data class ItemData(
        val pincode: String,
        val price: String,
        val city: String?,
        val deliveryContactNumber: String,
        val sellerId: String,
        var lat:String?=null,
        var lng:String?=null,
        var categorySellerData:SellerCategoryData
    )
    data class SellerCategoryData(var sellerCatergoryList:List<CategoryImage?>)
    data class CategoryImage(var name: String?,var image: String,var sellerSubCatergoryList:List<String?>)
}