package com.example.evvolitm.mappers

import com.example.evvolitm.data.local.cart.CartEntity
import com.example.evvolitm.data.local.cart.CartItemEntity
import com.example.evvolitm.data.local.cart.CartItemProductEntity
import com.example.evvolitm.data.local.cart.CartItemWithProduct
import com.example.evvolitm.domain.model.Cart
import com.example.evvolitm.domain.model.CartItem
import com.example.evvolitm.domain.model.CartItemProduct

// Convert from CartEntity to Cart domain model
fun CartEntity.toDomain(cartItemsWithProduct: List<CartItemWithProduct>): Cart {
    return Cart(
        id = id,
        cartItems = cartItemsWithProduct.map { it.toDomain() }
    )
}

// Convert from CartItemWithProduct to CartItem domain model
fun CartItemWithProduct.toDomain(): CartItem {
    return CartItem(
        id = cartItem.id,
        productId = cartItem.productId,
        quantity = cartItem.quantity,
        product = product.toDomain()
    )
}

// Convert from CartItemProductEntity to CartItemProduct domain model
fun CartItemProductEntity.toDomain(): CartItemProduct {
    return CartItemProduct(
        id = id,
        title = title,
        titleEn = titleEn,
        titleRu = titleRu,
        model = model,
        slug = slug,
        imageUrl = imageUrl,
        price = price,
        salePrice = salePrice
    )
}

// Convert from Cart domain model to CartEntity
fun Cart.toEntity(): CartEntity {
    return CartEntity(
        id = id
    )
}

// Convert from CartItem domain model to CartItemEntity
fun CartItem.toEntity(cartOwnerId: Long): CartItemEntity {
    return CartItemEntity(
        id = id,
        productId = productId,
        quantity = quantity,
        cartOwnerId = cartOwnerId
    )
}

// Convert from CartItemProduct domain model to CartItemProductEntity
fun CartItemProduct.toEntity(): CartItemProductEntity {
    return CartItemProductEntity(
        id = id,
        title = title,
        titleEn = titleEn,
        titleRu = titleRu,
        model = model,
        slug = slug,
        imageUrl = imageUrl,
        price = price,
        salePrice = salePrice
    )
}


