package com.example.inventoryapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.inventoryapp.models.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM items")
    fun getAllItems(): Flow<List<Item>>

    @Query("SELECT * FROM items WHERE id=:id")
    fun getItemById(id: Int) : Flow<Item?>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateItem(item: Item)

}