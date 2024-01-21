package com.grocery.mandixpress.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CartItems")
data class CartItems(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "ProductIdNumber")
    val productIdNumber: String?=null,
    @ColumnInfo(name = "strCategoryThumb")
    val strCategoryThumb: String?=null,
    @ColumnInfo(name = "totalCount")
    val totalCount: Int?=0,
    @ColumnInfo(name = "productPrice")
    var strProductPrice: Int?=0,

    @ColumnInfo(name = "strProductName")
    var strProductName: String?=null,
    @ColumnInfo(name = "actualprice")
    var actualprice: String?=null,
    @ColumnInfo(name = "SavingAmount")
    var savingAmount: Int?=null,
    @ColumnInfo(name = "sellerId")
    var sellerId:String?=null,
    @ColumnInfo(name = "lat")
    var lat:Double?=null,
    @ColumnInfo(name = "lng")
    var lng:Double?=null,



)
{
    constructor(
        ProductIdNumber: String?=null,
        strCategoryThumb: String?=null,
        totalCount: Int?=null,
        price:Int?=null,
        strCategoryDescription: String?=null,
        actualprice: String?=null,
         savingAmount: String?=null,
        sellerId: String?=null,
        lat:Double?=null,
        lng:Double?=null


        ) : this(0, ProductIdNumber, strCategoryThumb, totalCount, price,strCategoryDescription,actualprice,savingAmount?.toInt(),sellerId,lat,lng)
}


