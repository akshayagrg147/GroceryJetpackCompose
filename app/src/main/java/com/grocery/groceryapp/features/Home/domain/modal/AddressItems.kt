package com.grocery.groceryapp.features.Home.domain.modal

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AddressItems")
data class AddressItems(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "customer_name")
    val customer_name: String,
    @ColumnInfo(name = "customer_PhoneNumber")
    val customer_PhoneNumber: String,
    @ColumnInfo(name = "PinCode")
    val PinCode: Int,
    @ColumnInfo(name = "Address1")
    val Address1: String,
    @ColumnInfo(name = "Address2")
    val Address2: String,
    @ColumnInfo(name = "LandMark")
    val LandMark: String,




    )
{
    constructor(customer_name: String, customer_PhoneNumber: String,PinCode: Int,Address1: String,Address2: String,LandMark: String) : this(0, customer_name, customer_PhoneNumber,PinCode,Address1,Address2,LandMark)
}
