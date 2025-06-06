package com.example.evvolitm.domain.repository

import com.example.evvolitm.data.local.cart.CartEntity
import com.example.evvolitm.data.local.cart.CartItemEntity
import com.example.evvolitm.data.local.cart.CartWithCartItemsAndProducts
import com.example.evvolitm.domain.model.Cart
import com.example.evvolitm.domain.model.CartItem
import com.example.evvolitm.domain.model.CartItemProduct
import com.example.evvolitm.util.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun createEmptyCartEntity(): CartEntity?

    suspend fun getCartById(id: Long): CartEntity?

    suspend fun getCartWithItemsAndProducts(cartId: Long): CartWithCartItemsAndProducts

    suspend fun getLatestCartEntity(): CartEntity?

    suspend fun addOrUpdateCartItemEntity(
        cartId: Long,
        quantity: Int,
        cartItemProduct: CartItemProduct,
    )

    suspend fun deleteAllCarts()

    suspend fun getCartItemByProductIdAndCartId(productId: String, cartId: Long): CartItemEntity?

    suspend fun deleteCartItem(cartItemId: Long)

    suspend fun deleteCartItemProductById(productId: String)


}
