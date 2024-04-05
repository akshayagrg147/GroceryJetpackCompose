package com.grocery.mandixpress.roomdatabase

data class CartItemPriceBySeller(
    val sellerId: String?=null,
    val totalItemPrice: Int?=-1,
    val freedeliveryPrice: Int?=-1
)