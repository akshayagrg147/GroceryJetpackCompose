package com.grocery.mandixpress.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity(tableName = "AdminAccess")
data class AdminAccessTable(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "pincode")
    val pincode: String?=null,
    @ColumnInfo(name = "price")
    val price: String?=null,
    @ColumnInfo(name = "city")
    val city: String?=null,
    @ColumnInfo(name = "sellerId")
    var sellerId: String?=null,

    @ColumnInfo(name = "deliveryContactNumber")
    var deliveryContactNumber: String?=null,
    @ColumnInfo(name = "lat")
    var latitude: String?=null,
    @ColumnInfo(name = "lng")
    var longitude: String?=null,



)
{
    constructor(
        pincode: String?=null,
        price: String?=null,
        city: String?=null,
        sellerId:String?=null,
        deliveryContactNumber: String?=null,
        latitude: String?=null,
        longitude: String?=null,


        ) : this(0, pincode, price, city,sellerId,deliveryContactNumber,latitude,longitude)
}


