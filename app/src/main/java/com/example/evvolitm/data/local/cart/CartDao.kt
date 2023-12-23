package com.example.evvolitm.data.local.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update


@Dao
interface CartDao {
    @Transaction
    @Query("SELECT * FROM carts WHERE cartId = :cartId")
    suspend fun getCartWithItemsAndProducts(cartId: Long): CartWithCartItemsAndProducts

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartEntity): Long

    @Query("SELECT * FROM carts WHERE cartId = :id")
    suspend fun getCartById(id: Long): CartEntity?

    @Query("SELECT * FROM carts ORDER BY cartId DESC LIMIT 1")
    suspend fun getLatestCart(): CartEntity?

    @Update
    suspend fun updateCart(cart: CartEntity)

    @Delete
    suspend fun deleteCart(cart: CartEntity)

}

