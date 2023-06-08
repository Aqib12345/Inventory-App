package com.example.inventoryapp.data

import com.example.inventoryapp.models.Item
import javax.inject.Inject

class Repository @Inject constructor(private val appDao: ItemDao) {
    fun getAllItems() = appDao.getAllItems()

    fun getItemById(id: Int) = appDao.getItemById(id)

    suspend fun insertItem(item: Item) = appDao.insertItem(item)

    suspend fun deleteItem(item: Item) = appDao.deleteItem(item)

    suspend fun updateItem(item: Item) = appDao.updateItem(item)
}