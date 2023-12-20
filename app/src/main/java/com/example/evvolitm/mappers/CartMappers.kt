package com.example.evvolitm.mappers

import com.example.evvolitm.data.local.cart.CartEntity
import com.example.evvolitm.data.local.cart.CartItemEntity
import com.example.evvolitm.data.local.cart.CartItemProductEntity
import com.example.evvolitm.domain.model.Cart
import com.example.evvolitm.domain.model.CartItem
import com.example.evvolitm.domain.model.CartItemProduct

fun CartEntity.toCart(
    cartItems: MutableList<CartItemEntity>,
    productMap: MutableMap<String?, CartItemProduct?>
): Cart {
    return Cart(
        id = this.id,
        cartItems = cartItems.map { it.toCartItem(productMap[it.productId]) }.toMutableList()
    )
}

fun CartItemEntity.toCartItem(product: CartItemProduct?): CartItem {
    return CartItem(
        id = id,
        productId = productId,
        quantity = quantity,
        product = product
    )
}

fun CartItemProductEntity.toCartItemProduct(): CartItemProduct {
    return CartItemProduct(
        id = this.id,
        price = this.price,
        salePrice = this.salePrice
    )
}


fun Cart.toCartEntity(): CartEntity {
    return CartEntity(
        id = this.id
    )
}

fun CartItem.toCartItemEntity(cartId: kotlin.Long): CartItemEntity {
    return CartItemEntity(
        id = this.id,
        productId = this.productId,
        quantity = this.quantity,
        cartOwnerId = cartId
    )
}

fun CartItemProduct.toCartItemProductEntity(): CartItemProductEntity {
    return CartItemProductEntity(
        id = this.id,
        price = this.price,
        salePrice = this.salePrice
    )
}

