package com.example.evvolitm.data.repository

import com.example.evvolitm.data.local.EvvoliTmDatabase
import com.example.evvolitm.data.local.cart.CartDao
import com.example.evvolitm.data.local.cart.CartEntity
import com.example.evvolitm.data.local.cart.CartItemDao
import com.example.evvolitm.data.local.cart.CartItemEntity
import com.example.evvolitm.data.local.cart.CartItemProductDao
import com.example.evvolitm.data.local.cart.CartItemProductEntity
import com.example.evvolitm.data.remote.EvvoliTmApi
import com.example.evvolitm.domain.model.Cart
import com.example.evvolitm.domain.model.CartItem
import com.example.evvolitm.domain.model.CartItemProduct
import com.example.evvolitm.domain.repository.CartRepository
import com.example.evvolitm.mappers.toCart
import com.example.evvolitm.mappers.toCartEntity
import com.example.evvolitm.mappers.toCartItem
import com.example.evvolitm.mappers.toCartItemEntity
import com.example.evvolitm.mappers.toCartItemProduct
import com.example.evvolitm.mappers.toCartItemProductEntity
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

    override suspend fun getLatestCartEntity(): CartEntity? {
        return cartDao.getLatestCart()
    }

    override suspend fun getCartById(cartId: Long): Cart? {
        return withContext(Dispatchers.IO) {
            try {
                val cartEntityWithCartItemEntities = cartDao.getCartWithCartItems(cartId = cartId)
                val cartItemEntities =
                    cartEntityWithCartItemEntities.cartItemEntities.toMutableList()
                val cartProductItemEntities = getCartItemProductListByProductIdList(
                    cartItemEntities.map { it.productId }
                )
                cartEntityWithCartItemEntities.let {
                    val productMap = cartProductItemEntities
                        .associateBy { product -> product?.id }
                        .toMutableMap()
                    cartEntityWithCartItemEntities.cartEntity.toCart(
                        cartItems = cartItemEntities,
                        productMap = productMap
                    )
                }
            } catch (e: Exception) {
                null
            }
        }
    }

    override suspend fun getCartProductItemById(cartProductItemId: String): CartItemProduct? {
        return cartItemProductDao.getCartItemProductById(cartProductItemId)?.toCartItemProduct()
    }


    override suspend fun getFlowResourceCartById(cartId: Long): Flow<Resource<Cart>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val cartEntityWithCartItemEntities = cartDao.getCartWithCartItems(cartId = cartId)
                val cartItemEntities =
                    cartEntityWithCartItemEntities.cartItemEntities.toMutableList()
                val cartProductItemEntities = getCartItemProductListByProductIdList(
                    cartItemEntities.map { it.productId }
                )
                cartEntityWithCartItemEntities.let {
                    val productMap = cartProductItemEntities
                        .associateBy { product -> product?.id }
                        .toMutableMap()
                    val cart = cartEntityWithCartItemEntities.cartEntity.toCart(
                        cartItems = cartItemEntities,
                        productMap = productMap
                    )
                    emit(Resource.Success(data = cart))
                    emit(Resource.Loading(false))
                    return@flow
                }
            } catch (e: Exception) {
                println("e = $e")
                emit(Resource.Error("Error no such movie"))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCartItemListByCartId(cartId: Long): Flow<Resource<List<CartItem>>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val cartEntityWithCartItemEntities = cartDao.getCartWithCartItems(cartId = cartId)
                val cartEntity = cartEntityWithCartItemEntities.cartEntity
                val cartItemEntities = cartEntityWithCartItemEntities.cartItemEntities

                emit(Resource.Success(
                    data = cartItemEntities.map {
                        it.toCartItem(
                            product = cartItemProductDao.getCartItemProductById(it.productId)
                                ?.toCartItemProduct()
                        )
                    }
                ))
                emit(Resource.Loading(false))
            } catch (e: Exception) {
                println("e = $e")
                emit(Resource.Error("Error no such movie"))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getCartItemProductListByProductIdList(
        ids: List<String>
    ): List<CartItemProduct?> {
        return ids.map {
            cartItemProductDao.getCartItemProductById(it)?.toCartItemProduct()
        }
    }


    override suspend fun addOrUpdateCartItemEntity(
        cartId: Long,
        productId: String,
        price: String,
        salePrice: String,
        quantity: Int
    ) {
        withContext(Dispatchers.IO) {
            try {
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
                    println(" in else if (quantity > 0): existingCartItem = $existingCartItem")
                    // Create new CartItemProduct if it doesn't exist
                    val productExists = cartItemProductDao.getCartItemProductById(productId) != null
                    if (!productExists) {
                        cartItemProductDao.insertCartItemProduct(
                            CartItemProductEntity(
                                id = productId,
                                price = price.toDouble(),
                                salePrice = salePrice.toDouble()
                            )
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


    override suspend fun updateCartEntityByCart(cart: Cart) {
        withContext(Dispatchers.IO) {
            val cartEntity = cart.toCartEntity()
            cartDao.updateCart(cartEntity)

            cart.cartItems.forEach { cartItem ->
                val cartItemEntity = cartItem.toCartItemEntity(cartEntity.id)
                if (cartItem.quantity > 0) {
                    cartItemDao.updateCartItem(cartItemEntity)
                } else {
                    cartItemDao.deleteCartItem(cartItemEntity)
                }

                val cartItemProductEntity = cartItem.product?.toCartItemProductEntity()
                cartItemProductEntity?.let {
                    cartItemProductDao.updateCartItemProduct(it)
                }
            }
        }
    }
}

