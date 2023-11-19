package com.grocery.mandixpress.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "CartItems")
data class CartItems(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "ProductIdNumber")
    val ProductIdNumber: String?=null,
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
    var sellerId:String?=null


)
{
    constructor(
        ProductIdNumber: String?,
        strCategoryThumb: String?,
        totalCount: Int?,
        price:Int?,
        strCategoryDescription: String?,
        actualprice: String?,
         savingAmount: String?=null,
        sellerId: String?,

        ) : this(0, ProductIdNumber, strCategoryThumb, totalCount, price,strCategoryDescription,actualprice,savingAmount?.toInt(),sellerId)
}


