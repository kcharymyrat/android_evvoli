package com.example.evvolitm.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.evvolitm.data.local.category.CategoryDao
import com.example.evvolitm.data.local.category.CategoryEntity
import com.example.evvolitm.data.local.product.ProductInDetailsDao
import com.example.evvolitm.data.local.product.ProductInDetailsEntity
import com.example.evvolitm.data.local.product.ProductImageDao
import com.example.evvolitm.data.local.product.ProductImageEntity
import com.example.evvolitm.data.local.product.ProductSpecDao
import com.example.evvolitm.data.local.product.ProductSpecEntity

@Database(
    entities = [
        CategoryEntity::class,
        ProductInDetailsEntity::class,
        ProductImageEntity::class,
        ProductSpecEntity::class,
    ],
    version = 1
)
abstract class EvvoliTmDatabase: RoomDatabase() {
    abstract val categoryDao: CategoryDao
    abstract val productInDetailsDao: ProductInDetailsDao
    abstract val productImageDao: ProductImageDao
    abstract val productSpecDao: ProductSpecDao
}