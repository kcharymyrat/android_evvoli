package com.example.evvolitm.data.local.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartItemProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItemProduct(product: CartItemProductEntity)

    @Query("SELECT * FROM products WHERE id = :productId")
    suspend fun getCartItemProductById(productId: String): CartItemProductEntity?

    @Update
    suspend fun updateCartItemProduct(cartItemProductEntity: CartItemProductEntity)

    @Query("DELETE FROM cart_item_products WHERE id = :productId")
    suspend fun deleteCartItemProductById(productId: String)
}

