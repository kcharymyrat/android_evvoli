package com.example.evvolitm.presentation

import com.example.evvolitm.data.local.cart.CartItemWithProduct
import com.example.evvolitm.domain.model.CartItem

data class CartScreenState(
    var id: Long? = null,
    var isLoading: Boolean = false,
    var cartItems:  List<CartItemWithProduct> = mutableListOf(),
    var cartQty: Int = 0,
    var cartTotalPrice:Double = 0.00
)