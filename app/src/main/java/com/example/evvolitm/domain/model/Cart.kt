package com.example.evvolitm.domain.model

data class Cart(
    val id: Long,
    var cartItems: MutableList<CartItem> = mutableListOf()
)

