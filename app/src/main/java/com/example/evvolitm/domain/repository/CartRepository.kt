package com.example.evvolitm.domain.repository

import com.example.evvolitm.data.local.cart.CartEntity
import com.example.evvolitm.domain.model.Cart
import com.example.evvolitm.domain.model.CartItem
import com.example.evvolitm.domain.model.CartItemProduct
import com.example.evvolitm.util.Resource
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun createEmptyCartEntity(): CartEntity?

    suspend fun getLatestCartEntity(): CartEntity?

    suspend fun getCartById(cartId: Long): Cart?

    suspend fun getCartProductItemById(cartProductItemId: String): CartItemProduct?

    suspend fun getFlowResourceCartById(cartId: Long): Flow<Resource<Cart>>

    suspend fun getCartItemListByCartId(cartId: Long): Flow<Resource<List<CartItem>>>

    suspend fun getCartItemProductListByProductIdList(ids: List<String>): List<CartItemProduct?>

    suspend fun addOrUpdateCartItemEntity(
        cartId: Long,
        productId: String,
        imageUrl: String?,
        price: String,
        salePrice: String,
        quantity: Int
    )

    suspend fun updateCartEntityByCart(cart: Cart)
}
