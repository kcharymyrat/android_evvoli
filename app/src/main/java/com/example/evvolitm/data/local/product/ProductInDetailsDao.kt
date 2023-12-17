package com.example.evvolitm.data.local.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface ProductInDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductInDetailsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductList(categoryList: List<ProductInDetailsEntity>)

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductById(productId: String): ProductInDetailsEntity

    @Query("SELECT * FROM products WHERE slug = :slug")
    suspend fun getProductBySlug(slug: String): ProductInDetailsEntity?

    @Query("DELETE FROM products")
    suspend fun deleteAllProducts()

    @Query("DELETE FROM products WHERE categoryId = :categoryId")
    suspend fun deleteProductsByCategory(categoryId: String)

    @Transaction
    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getProductWithSpecsAndImages(productId: String): ProductWithSpecsAndImages
}

