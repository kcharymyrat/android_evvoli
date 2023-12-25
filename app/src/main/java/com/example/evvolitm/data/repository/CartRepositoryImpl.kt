package com.example.evvolitm.data.repository

import com.example.evvolitm.data.local.EvvoliTmDatabase
import com.example.evvolitm.data.local.cart.CartDao
import com.example.evvolitm.data.local.cart.CartEntity
import com.example.evvolitm.data.local.cart.CartItemDao
import com.example.evvolitm.data.local.cart.CartItemEntity
import com.example.evvolitm.data.local.cart.CartItemProductDao
import com.example.evvolitm.data.local.cart.CartItemProductEntity
import com.example.evvolitm.data.local.cart.CartWithCartItemsAndProducts
import com.example.evvolitm.data.remote.EvvoliTmApi
import com.example.evvolitm.domain.model.Cart
import com.example.evvolitm.domain.model.CartItem
import com.example.evvolitm.domain.model.CartItemProduct
import com.example.evvolitm.domain.repository.CartRepository
import com.example.evvolitm.mappers.toEntity
import com.example.evvolitm.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartRepositoryImpl  @Inject constructor(
    private val evvoliTmApi: EvvoliTmApi,
    evvoliTmDb: EvvoliTmDatabase,
): CartRepository {
    private val cartDao: CartDao = evvoliTmDb.cartDao
    private val cartItemDao: CartItemDao = evvoliTmDb.cartItemDao
    private val cartItemProductDao: CartItemProductDao = evvoliTmDb.cartItemProductDao

    override suspend fun createEmptyCartEntity(): CartEntity? {
        val newCart = CartEntity()
        val cartId = cartDao.insertCart(newCart)
        return cartDao.getCartById(id = cartId)
    }

    override suspend fun getCartWithItemsAndProducts(cartId: Long): CartWithCartItemsAndProducts {
        return cartDao.getCartWithItemsAndProducts(cartId)
    }

    override suspend fun getLatestCartEntity(): CartEntity? {
        return cartDao.getLatestCart()
    }

    override suspend fun addOrUpdateCartItemEntity(
        cartId: Long,
        quantity: Int,
        cartItemProduct: CartItemProduct,
    ) {
        withContext(Dispatchers.IO) {
            try {
                val productId = cartItemProduct.id
                // Check if the cart item already exists
                val existingCartItem = cartItemDao.getCartItemByProductIdAndCartId(productId, cartId)

                if (existingCartItem != null) {
                    // If exists and new quantity is greater than 0, update it
                    val newQuantity = existingCartItem.quantity + quantity
                    if (newQuantity > 0) {
                        cartItemDao.updateCartItem(existingCartItem.copy(quantity = newQuantity))
                    } else {
                        // If new quantity is 0 or less, delete the cart item
                        cartItemDao.deleteCartItem(existingCartItem)
                    }
                } else if (quantity > 0) {
                    // Create new CartItemProduct if it doesn't exist
                    val productExists = cartItemProductDao.getCartItemProductById(productId) != null
                    if (!productExists) {
                        cartItemProductDao.insertCartItemProduct(
                            cartItemProduct.toEntity()
                        )
                    }

                    // Create new Cart if it doesn't exist
                    if (cartDao.getCartById(cartId) == null) {
                        cartDao.insertCart(CartEntity())
                    }

                    // Insert new CartItem
                    cartItemDao.insertCartItem(
                        CartItemEntity(
                            productId = productId,
                            quantity = quantity,
                            cartOwnerId = cartId
                        )
                    )
                }
            } catch (e: Exception) {
                // Handle exceptions, e.g., log error or rethrow a custom exception
                println("Error in addOrUpdateCartItemEntity: $e")
            }
        }
    }

    override suspend fun deleteAllCarts() {
        cartDao.deleteAllCarts()
    }

}

