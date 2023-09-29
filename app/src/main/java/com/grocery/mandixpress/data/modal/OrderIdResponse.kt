package com.grocery.mandixpress.data.modal




import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class OrderIdResponse(
    @Json(name = "message")
    val message: String?=null,
    @Json(name = "ProductResponse")
    val productResponse: ProductResponse?=null,
    @Json(name = "statusCode")
    val statusCode: Int?=0
):Parcelable {
    @Parcelize
    @JsonClass(generateAdapter = true)
    data class ProductResponse(
        @Json(name = "address")
        val address: String?=null,
        @Json(name = "createdDate")
        val createdDate: String?=null,
        @Json(name = "mobilenumber")
        val mobilenumber: String?=null,
        @Json(name = "orderId")
        val orderId: String?=null,
        @Json(name = "orderList")
        val orderList: List<Order?>?,
        @Json(name = "paymentmode")
        val paymentmode: String?=null,
        @Json(name = "totalOrderValue")
        val totalOrderValue: String?=null
    ) :Parcelable{
        @Parcelize
        @JsonClass(generateAdapter = true)
        data class Order(
            @Json(name = "productId")
            val productId: String?=null,
            @Json(name = "product_name")
            val productName: String?=null,
            @Json(name = "productprice")
            val productprice: String?=null,
            @Json(name = "quantity")
            val quantity: String?=null
        ):Parcelable
    }
}