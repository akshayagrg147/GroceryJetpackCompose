package com.grocery.groceryapp.RoomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.*

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

        ) : this(0, ProductIdNumber, strCategoryThumb, totalCount, price,strCategoryDescription,actualprice,savingAmount?.toInt())
}


