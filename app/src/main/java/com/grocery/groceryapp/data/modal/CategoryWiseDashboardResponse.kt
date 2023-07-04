package com.grocery.groceryapp.data.modal


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class  CategoryWiseDashboardResponse(
    @Json(name = "itemData")
    val list: List<cat>?,
    @Json(name = "message")
    val message: String?,
    @Json(name = "statusCode")
    val statusCode: Int?
) {
    @JsonClass(generateAdapter = true)
    data class cat(

        @Json(name = "categoryTitle")
    val categoryTitle: String?,
        @Json(name = "category")
        val category: String?,
        @Json(name = "ls")
        val ls: List<L?>?
    ) {
        @JsonClass(generateAdapter = true)
        data class L(
            @Json(name = "actual_price")
            val actualPrice: String?,
            @Json(name = "category")
            val category: String?,
            @Json(name = "DashboardDisplay")
            val dashboardDisplay: Boolean?,
            @Json(name = "price")
            val price: String?,
            @Json(name = "ProductDescription")
            val productDescription: String?,
            @Json(name = "productId")
            val productId: String?,
            @Json(name = "ProductImage1")
            val productImage1: String?,
            @Json(name = "ProductImage2")
            val productImage2: String?,
            @Json(name = "ProductImage3")
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