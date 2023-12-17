package com.example.evvolitm.data.local.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.evvolitm.data.local.category.CategoryEntity

@Dao
interface ProductImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductImage(image: ProductImageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductImageList(images: List<ProductImageEntity>)

    @Update
    suspend fun updateProductImage(image: ProductImageEntity)

    @Query("SELECT * FROM product_images WHERE productId = :productId")
    suspend fun getProductImagesByProductId(productId: String): List<ProductImageEntity>

    @Query("SELECT * FROM product_images WHERE id = :id")
    suspend fun getProductImageById(id: String): ProductImageEntity?

    @Query("DELETE FROM product_images")
    suspend fun deleteAllProductImages()
}
