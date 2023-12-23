package com.example.evvolitm.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.evvolitm.domain.model.CartItem
import com.example.evvolitm.domain.repository.CartRepository
import com.example.evvolitm.domain.repository.CategoryRepository
import com.example.evvolitm.domain.repository.ProductDetailRepository
import com.example.evvolitm.domain.repository.ProductRepository
import com.example.evvolitm.mappers.toCart
import com.example.evvolitm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val productDetailRepository: ProductDetailRepository,
    private val cartRepository: CartRepository,
): AndroidViewModel(application) {
    private val _categoryScreenState = MutableStateFlow(CategoryScreenState())
    val categoryScreenState = _categoryScreenState.asStateFlow()

    private val _productScreenState = MutableStateFlow(ProductScreenState())
    val productScreenState = _productScreenState.asStateFlow()

    private val _productDetailScreenState = MutableStateFlow(ProductDetailScreenState())
    val productDetailScreenState = _productDetailScreenState.asStateFlow()

    private val _cartScreenState = MutableStateFlow(CartScreenState())
    val cartScreenState = _cartScreenState.asStateFlow()

    var productCategoryId: String? = null

    init {
        loadCategories(forceFetchFromRemote = false)
    }



    fun onCategoryScreenEvent(event: CategoryScreenEvents) {
        println("event = $event")
        when(event) {
            is CategoryScreenEvents.Refresh -> {
                loadCategories(forceFetchFromRemote = true)
            }

            is CategoryScreenEvents.OnPaginate -> {
                _categoryScreenState.value.page++
                loadCategories(forceFetchFromRemote = true)
            }

        }
    }


    fun onProductScreenEvent(event: ProductScreenEvents, categoryId: String) {
        println("event = $event")
        when(event) {
            is ProductScreenEvents.Refresh -> {
                loadCategoryProducts(categoryId = categoryId, forceFetchFromRemote = true)
            }

            is ProductScreenEvents.OnPaginate -> {
                productScreenState.value.page++
                loadCategoryProducts(categoryId = categoryId, forceFetchFromRemote = true)
            }

        }
    }


    fun onProductDetailScreenEvent(event: ProductDetailScreenEvents, productId: String) {
        println("event = $event")
        when(event) {
            is ProductDetailScreenEvents.Refresh -> {
                loadProductDetail(productId = productId, forceFetchFromRemote = true)
            }
        }
    }


    private fun loadCategories(
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {

            println("_cartScreenState.value.id = ${_cartScreenState.value.id}")
            if (_cartScreenState.value.id == null) {
                createCartScreenState()
                println("new: _cartScreenState.value = ${_cartScreenState.value}")
            }
            println("in loadCategories: _cartScreenState.value = ${_cartScreenState.value}")

            _categoryScreenState.update {
                it.copy(isLoading = true)
            }

            categoryRepository.getCategories(
                fetchFromRemote = forceFetchFromRemote,
                isRefresh = isRefresh,
                page = categoryScreenState.value.page
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Success -> {
                        result.data?.let { categoryList ->
                            _categoryScreenState.update {
                                it.copy(
                                    categoryList = categoryScreenState.value.categoryList
                                            + categoryList
                                )
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _categoryScreenState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }


    fun loadCategoryProducts(
        categoryId: String,
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {

            println("_cartScreenState.value.id = ${_cartScreenState.value.id}")
            if (_cartScreenState.value.id == null) {
                createCartScreenState()
                println("new: _cartScreenState.value = ${_cartScreenState.value}")
            }
            println("in loadCategoryProducts: _cartScreenState.value = ${_cartScreenState.value}")

            _productScreenState.update {
                it.copy(isLoading = true)
            }

            productRepository.getProducts(
                categoryId = categoryId,
                fetchFromRemote = forceFetchFromRemote,
                isRefresh = isRefresh,
                page = productScreenState.value.page
            ).collect { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Success -> {

                        result.data?.let { productList ->
                            Log.d("Nav", "loadCategoryProducts => productList = $productList")
                            _productScreenState.update {
                                it.copy(
                                    productList = productScreenState.value.productList
                                            + productList
                                )
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _productScreenState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }


    fun loadProductDetail(
        productId: String,
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {

            println("_cartScreenState.value.id = ${_cartScreenState.value.id}")
            if (_cartScreenState.value.id == null) {
                createCartScreenState()
                println("new: _cartScreenState.value = ${_cartScreenState.value}")
            }
            println("in loadCategories: _cartScreenState.value = ${_cartScreenState.value}")

            _productDetailScreenState.update {
                it.copy(isLoading = true)
            }

            productDetailRepository.getProductDetail(
                productId = productId,
                fetchFromRemote = forceFetchFromRemote,
                isRefresh = isRefresh,
            ).collect { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Success -> {
                        result.data?.let {productDetail ->
                            Log.d("Nav", "loadProductDetail => productDetail = $productDetail")
                            _productDetailScreenState.update {
                                it.copy(
                                    productDetail = productDetail,
                                    productImageList = productDetail.images,
                                    productSpecList = productDetail.specs
                                )
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _productScreenState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }


    // CART LOGIC ------------------------------------

    fun loadCart() {
        viewModelScope.launch {
            _cartScreenState.update {
                it.copy(isLoading = true)
            }

            var cartId = _cartScreenState.value.id
            if (cartId == null) {
                val cartEntity = cartRepository.createEmptyCartEntity()
                cartId = cartEntity?.id
                _cartScreenState.value.id = cartId
            }

            if (cartId != null) {
                cartRepository.getFlowResourceCartById(
                    cartId = cartId
                ).collectLatest { result ->
                    when (result) {
                        is Resource.Error -> Unit
                        is Resource.Success -> {
                            result.data?.let {
                                _cartScreenState.value.cartItems = it.cartItems
                                _cartScreenState.value.cartQty = it.cartItems.sumOf { item -> item.quantity }
                            }
                        }
                        is Resource.Loading -> {
                            _cartScreenState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }
                    }
                }
            }
        }
    }

    fun updateCart(
        productId: String,
        imageUrl: String?,
        price: String,
        salePrice: String,
        isMinus: Boolean = false
    ) {
        viewModelScope.launch {
            println("in updateCart: _cartScreenState.value = ${_cartScreenState.value}")

            val quantity = if(isMinus) -1 else 1

            var cartId = _cartScreenState.value.id
            if (cartId == null) {
                createCartScreenState()
                cartId = _cartScreenState.value.id
            }

            if (cartId != null) {
                cartRepository.addOrUpdateCartItemEntity(
                    cartId = cartId,
                    productId = productId,
                    imageUrl = imageUrl,
                    price = price,
                    salePrice = salePrice,
                    quantity = quantity
                )

                val cart = cartRepository.getCartById(cartId)

                // Create a new instance with the updated values
                val newCartState = _cartScreenState.value.copy(
                    cartItems = cart?.cartItems?.toMutableList() ?: mutableListOf(),
                    cartQty = cart?.cartItems?.sumOf { it.quantity } ?: 0,
                    cartTotalPrice = cart?.cartItems?.sumOf { cartItem ->
                        (cartItem.product?.salePrice ?: 0.0) * (cartItem.quantity ?: 0)
                    } ?: 0.00
                )

                // Update the state
                _cartScreenState.value = newCartState

                println("_cartScreenState.value = ${_cartScreenState.value}")

            }

            println("in updateCart: _cartScreenState.value = ${_cartScreenState.value}")
        }
    }


    private suspend fun createCartScreenState() {
        val cartEntity = cartRepository.getLatestCartEntity() ?: cartRepository.createEmptyCartEntity()
        cartEntity?.let {
            val newCart = cartRepository.getCartById(it.id)
            newCart?.let { cart ->
                _cartScreenState.value = _cartScreenState.value.copy(
                    id = cart.id,
                    cartItems = cart.cartItems,
                    cartQty = cart.cartItems.sumOf { item -> item.quantity },
                    cartTotalPrice = cart.cartItems.sumOf {
                            item -> item.quantity * (item.product?.salePrice ?: 0.00)
                    }
                )
            }
        }
    }

    fun updateCartScreenState(newCartItems: List<CartItem>) {
        val newCartQty = newCartItems.sumOf { it.quantity }
        val newTotalPrice = newCartItems.sumOf { (it.product?.salePrice ?: 0.00 ) * it.quantity }

        // Create a new instance with the updated values
        val newCartState = _cartScreenState.value.copy(
            cartItems = newCartItems.toMutableList(),
            cartQty = newCartQty,
            cartTotalPrice = newTotalPrice
        )

        // Update the state
        _cartScreenState.value = newCartState
    }
}