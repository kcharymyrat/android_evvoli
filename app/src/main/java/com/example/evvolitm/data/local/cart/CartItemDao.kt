package com.example.evvolitm.data.local.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CartItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartItemEntity): Long

    @Query("SELECT * FROM cart_items WHERE id = :id")
    suspend fun getCartItemById(id: Long): CartItemEntity?

    @Query("SELECT * FROM cart_items WHERE cartOwnerId = :cartId")
    suspend fun getCartItemsByCartOwnerId(cartId: Long): List<CartItemEntity>

    @Query("UPDATE cart_items SET quantity = :quantity WHERE id = :cartItemId")
    suspend fun updateQuantity(cartItemId: Long, quantity: Int)

    @Query("DELETE FROM cart_items WHERE id = :cartItemId")
    suspend fun deleteCartItemById(cartItemId: Long)

    @Query("SELECT * FROM cart_items WHERE productId = :productId AND cartOwnerId = :cartId")
    suspend fun getCartItemByProductIdAndCartId(productId: String, cartId: Long): CartItemEntity?

    @Update
    suspend fun updateCartItem(cartItem: CartItemEntity)

    @Delete
    suspend fun deleteCartItem(cartItem: CartItemEntity)


}
