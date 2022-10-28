package com.grocery.groceryapp.data.modal




import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OrderIdResponse(
    @Json(name = "address")
    val address: String?=null,
    @Json(name = "message")
    val message: String?=null,
    @Json(name = "mobilenumber")
    val mobilenumber: String?=null,
    @Json(name = "paymentmode")
    val paymentmode: String?=null,
    @Json(name = "ProductResponse")
    val productResponse: List<ProductResponse?>?=null,
    @Json(name = "statusCode")
    val statusCode: Int?=null,
    @Json(name = "totalOrderValue")
    val totalOrderValue: String?=null
) {
    @JsonClass(generateAdapter = true)
    data class ProductResponse(
        @Json(name = "productId")
        val productId: String?,
        @Json(name = "product_name")
        val productName: String?,
        @Json(name = "productprice")
        val productprice: String?,
        @Json(name = "quantity")
        val quantity: String?
    )
}