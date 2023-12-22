package com.example.evvolitm.domain.model


data class CartItem(
    val id: Long,
    val productId: String,
    var quantity: Int,
    var product: CartItemProduct?
)

