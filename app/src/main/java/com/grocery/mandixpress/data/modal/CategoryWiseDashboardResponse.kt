package com.grocery.mandixpress.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class  CategoryWiseDashboardResponse(
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
            val orignalPrice: String?,
            @Json(name = "category")
            val category: String?,
            @Json(name = "DashboardDisplay")
            val dashboardDisplay: Boolean?,
            @Json(name = "selling_price")
            val selling_price: String?,
            @Json(name = "productDescription")
            val productDescription: String?,
            @Json(name = "productId")
            val productId: String?,
            @Json(name = "productImage1")
            val productImage1: String?,
            @Json(name = "productImage2")
            val productImage2: String?,
            @Json(name = "quantityInstructionController")
            val quantityInstructionController: String?,

            @Json(name = "productImage3")
            val productImage3: String?,
            @Json(name = "productName")
            val productName: String?,
            @Json(name = "quantity")
            val quantity: String?,
            @Json(name = "rating")
            val rating: List<Rating?>?
        ) {
            @JsonClass(generateAdapter = true)
            data class Rating(
                @Json(name = "customerId")
                val customerId: String?,
                @Json(name = "name")
                val name: String?,
                @Json(name = "rating")
                val rating: String?,
                @Json(name = "remark")
                val remark: String?
            )
        }
    }
}