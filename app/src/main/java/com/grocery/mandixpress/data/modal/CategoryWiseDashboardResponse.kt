package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CategoryWiseDashboardResponse(
    @Json(name = "itemData")
    val list: List<CategoryItem>?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "statusCode")
    val statusCode: Int?
) {
    @JsonClass(generateAdapter = true)
    data class CategoryItem(

        @Json(name = "categoryTitle")
        val categoryTitle: String?,
        @Json(name = "category")
        val category: String?,
        @Json(name = "ls")
        val ls: List<ItemData?>?
    ) {
        @JsonClass(generateAdapter = true)
        data class ItemData(
            @Json(name = "orignal_price")
            val orignalPrice: String?=null,
            @Json(name = "category")
            val category: String?=null,
            @Json(name = "DashboardDisplay")
            val dashboardDisplay: Boolean?=false,
            @Json(name = "selling_price")
            val selling_price: String?=null,
            @Json(name = "productDescription")
            val productDescription: String?=null,
            @Json(name = "productId")
            val productId: String?=null,
            @Json(name = "productImage1")
            val productImage1: String?=null,
            @Json(name = "productImage2")
            val productImage2: String?=null,
            @Json(name = "quantityInstructionController")
            val quantityInstructionController: String?=null,

            @Json(name = "productImage3")
            val productImage3: String?=null,
            @Json(name = "productName")
            val productName: String?=null,
            @Json(name = "quantity")
            val quantity: String?=null,
            @Json(name = "rating")
            val rating: List<Rating?>?= emptyList(),
            @Json(name = "sellerId")
            var sellerId:String?=null
        ) {
            @JsonClass(generateAdapter = true)
            data class Rating(
                @Json(name = "customerId")
                val customerId: String?=null,
                @Json(name = "name")
                val name: String?=null,
                @Json(name = "rating")
                val rating: String?=null,
                @Json(name = "remark")
                val remark: String?=null
            )
        }
    }
}