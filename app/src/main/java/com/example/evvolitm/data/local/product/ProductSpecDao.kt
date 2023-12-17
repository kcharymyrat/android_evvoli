package com.example.evvolitm.data.local.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductSpecDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductSpec(spec: ProductSpecEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductSpecList(specs: List<ProductSpecEntity>)

    @Update
    suspend fun updateProductSpec(spec: ProductSpecEntity)

    @Query("SELECT * FROM product_specs WHERE productId = :productId")
    suspend fun getProductSpecsByProductId(productId: String): List<ProductSpecEntity>

    @Query("SELECT * FROM product_specs WHERE id = :id")
    suspend fun getProductSpecById(id: String): ProductSpecEntity?

    @Query("DELETE FROM product_specs")
    suspend fun deleteAllProductSpecs()
}
