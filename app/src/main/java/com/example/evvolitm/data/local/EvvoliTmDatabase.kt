package com.example.evvolitm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.evvolitm.data.local.category.CategoryDao
import com.example.evvolitm.data.local.category.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 1
)
abstract class EvvoliTmDatabase: RoomDatabase() {
    abstract val categoryDao: CategoryDao
}