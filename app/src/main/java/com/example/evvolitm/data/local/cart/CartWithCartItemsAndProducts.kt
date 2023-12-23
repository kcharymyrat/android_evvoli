package com.example.evvolitm.data.local.cart

import androidx.room.Embedded
import androidx.room.Relation

data class CartWithCartItemsAndProducts(
    @Embedded val cart: CartEntity,
    @Relation(
        entity = CartItemEntity::class,
        parentColumn = "cartId",
        entityColumn = "cartOwnerId"
    )
    val cartItems: List<CartItemWithProduct>
)
