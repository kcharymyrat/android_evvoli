package com.example.evvolitm.data.local.cart

import androidx.room.Embedded
import androidx.room.Relation

data class CartWithCartItems(
    @Embedded val cartEntity: CartEntity,
    @Relation(
        parentColumn = "cartId",
        entityColumn = "cartOwnerId"
    )
    val cartItemEntities: List<CartItemEntity>
)
