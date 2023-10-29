package com.grocery.mandixpress.roomdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.grocery.mandixpress.features.home.domain.modal.AddressItems

@Database(entities = [AddressItems::class,CartItems::class,], version = 4)
abstract class AppDatabase : RoomDatabase() {
    abstract fun channelDao(): Dao

}