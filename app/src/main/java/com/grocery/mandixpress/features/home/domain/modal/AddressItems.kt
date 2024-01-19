package com.grocery.mandixpress.features.home.domain.modal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AddressItems")
data class AddressItems(
    @PrimaryKey(autoGenerate = true)
    val id: Long=1,
    @ColumnInfo(name = "customer_name")
    val customer_name: String?=null,
    @ColumnInfo(name = "customer_PhoneNumber")
    val customer_phoneNumber: String?=null,
    @ColumnInfo(name = "PinCode")
    val pinCode: Int?=-1,
    @ColumnInfo(name = "Address1")
    val address1: String?=null,
    @ColumnInfo(name = "Address2")
    val address2: String?=null,
    @ColumnInfo(name = "LandMark")
    val landMark: String?=null




    )
{
    constructor(customer_name: String, customer_PhoneNumber: String,PinCode: Int,Address1: String,Address2: String,LandMark: String) : this(0, customer_name, customer_PhoneNumber,PinCode,Address1,Address2,LandMark)
}
