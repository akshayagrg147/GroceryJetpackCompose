package com.grocery.groceryapp.RoomDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartItem(contact: CartItems)

    @Query("SELECT * FROM CartItems where ProductIdNumber=:ProductIdNumber")
    fun getProductBasedId( ProductIdNumber:String?):CartItems

    @Query("SELECT totalCount FROM CartItems where ProductIdNumber=:ProductIdNumber")
    fun getProductBasedIdCount( ProductIdNumber:String?): Flow<Int?>

    @Query("SELECT totalCount FROM CartItems where ProductIdNumber=:ProductIdNumber")
    fun getProductBasedIdCount1( ProductIdNumber:String?): Int?

    @Query("UPDATE CartItems SET totalCount=:count WHERE ProductIdNumber = :id")
    fun updateCartItem(count: Int,id:String?)

    @Query("SELECT SUM(totalCount) FROM CartItems")
    fun getTotalProductItems():Flow< Int>

    @Query("SELECT SUM(SavingAmount * totalCount) FROM CartItems")
    fun getTotalSavingAmount():Flow< Int?>?

    @Query("SELECT SUM(productPrice*totalCount) FROM CartItems")
     fun getTotalProductItemsPrice(): Flow<Int>

    @Query("DELETE  FROM CartItems WHERE ProductIdNumber = :id" )
    fun deleteCartItem(id:String?)

    @Query("SELECT * FROM CartItems")
    fun getAllCartItems():Flow<List<CartItems>>

    //address
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAddressItem(address: AddressItems)
    @Query("SELECT * FROM AddressItems")
    fun getAllAddress(): Flow<List<AddressItems>>
    @Query("DELETE  FROM AddressItems WHERE id = :id")
    fun deleteAddress(id:String?)

    @Query("DELETE FROM CartItems")
    fun deleteAllFromTable()

    @Query("UPDATE AddressItems SET customer_name=:customer_name, customer_PhoneNumber=:customer_PhoneNumber, PinCode=:PinCode, Address1=:Address1, Address2=:Address2,LandMark=:LandMark WHERE addressitems.id = :id")
    fun updateAddressItem(customer_name: String,customer_PhoneNumber: String,PinCode: Int,Address1: String,Address2: String,LandMark: String,id:Long?)


}