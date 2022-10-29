package com.grocery.groceryapp.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCartItem(contact: CartItems)

    @Query("SELECT * FROM CartItems where ProductIdNumber=:ProductIdNumber")
    fun getProductBasedId( ProductIdNumber:String?):CartItems

    @Query("SELECT totalCount FROM CartItems where ProductIdNumber=:ProductIdNumber")
    fun getProductBasedIdCount( ProductIdNumber:String?):Int

    @Query("UPDATE CartItems SET totalCount=:count WHERE ProductIdNumber = :id")
    fun updateCartItem(count: Int,id:String?)

    @Query("SELECT SUM(totalCount) FROM CartItems")
    fun getTotalProductItems():Flow< Int>

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

}