package com.grocery.groceryapp.RoomDatabase

import com.grocery.groceryapp.features.Home.domain.modal.AddressItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoRepository
@Inject
constructor(private val dao: Dao) {

    suspend fun insert(data: CartItems) = withContext(Dispatchers.IO) {
        dao.insertCartItem(data)
    }

    fun getCartItems(): Flow<List<CartItems>> = dao.getAllCartItems()
    fun getAddressItems(): Flow<List<AddressItems>> = dao.getAllAddress()
}