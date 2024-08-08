package com.grocery.mandixpress.roomdatabase

data class CartItemPriceBySeller(
    val sellerId: String?=null,
    val totalItemPrice: Int?=-1,
    val freedeliveryPrice: Int?=-1,
    val customerLat:Double?=0.00,
    val customerLng:Double?=0.00,
    val sellerLat:Double?=0.00,
    val sellerLng:Double?=0.00,

)