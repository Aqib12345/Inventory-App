package com.example.inventoryapp.di

import android.content.Context
import androidx.room.Room
import com.example.inventoryapp.data.AppDatabase
import com.example.inventoryapp.data.ItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesItemDao(appDatabase: AppDatabase): ItemDao = appDatabase.getDao()


    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context
    ): AppDatabase{
        return Room.databaseBuilder(
            context = context,
            AppDatabase::class.java,
            "inventory_db"
        ).build()
    }
}