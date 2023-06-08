package com.example.inventoryapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.inventoryapp.models.Item

@Database(entities = [Item::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun getDao(): ItemDao
}