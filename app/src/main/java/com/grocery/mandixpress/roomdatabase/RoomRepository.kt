package com.grocery.mandixpress.roomdatabase

import com.grocery.mandixpress.data.modal.AdminResponse
import com.grocery.mandixpress.features.home.domain.modal.AddressItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomRepository
@Inject
constructor(private val dao: Dao) {

    suspend fun insert(data: CartItems) = withContext(Dispatchers.IO) {
        dao.insertCartItem(data)
    }
    suspend fun deleteAllAdminAccessTable()= withContext(Dispatchers.IO){
        dao.deleteAllAdminAccessTable()
    }

    suspend fun insertAdminDetails(data: AdminAccessTable) = withContext(Dispatchers.IO) {
        dao.insertAdminDetails(data)
    }

    fun getCartItems(): Flow<List<CartItems>> = dao.getAllCartItems()
    fun getAddressItems(): Flow<List<AddressItems>> = dao.getAllAddress()
    suspend fun deleteAdminAccessItems() = withContext(Dispatchers.IO){
        dao.deleteAllAdminAccessTable()
    }

    suspend  fun deleteCartItems(productIdNumber: String?) = withContext(Dispatchers.IO) {

        var intger: Int =
            dao.getProductBasedIdCount(productIdNumber).first()?:0
        intger -= 1

        if (intger >= 1) {
            dao
                .updateCartItem(intger, productIdNumber)
        } else if (intger == 0) {
            dao
                .deleteCartItem(productIdNumber)

        }
    }
    fun getTotalProductItems(): Flow<Int?> = dao.getTotalProductItems()
    fun getTotalProductItemsPrice(): Flow<Int?>? = dao.getTotalProductItemsPrice()
    fun getTotalSavingAmount(): Flow<Int?>? = dao.getTotalSavingAmount()
    fun getProductBasedIdCount( productIdNumber: String): Flow<Int?> = dao.getProductBasedIdCount(productIdNumber)
    fun updateCartItem(i: Int, productIdNumber: String)=dao.updateCartItem(i, productIdNumber)



}