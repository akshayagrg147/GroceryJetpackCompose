package com.grocery.groceryapp.RoomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grocery.groceryapp.features.Home.domain.modal.AddressItems

@Database(entities = [AddressItems::class,CartItems::class,], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun channelDao(): Dao

}