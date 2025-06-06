package com.example.evvolitm.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.evvolitm.data.remote.respond.order_dtos.OrderDto
import com.example.evvolitm.domain.model.CartItemProduct
import com.example.evvolitm.domain.repository.CartRepository
import com.example.evvolitm.domain.repository.CategoryRepository
import com.example.evvolitm.domain.repository.OrderRepository
import com.example.evvolitm.domain.repository.ProductDetailRepository
import com.example.evvolitm.domain.repository.ProductRepository
import com.example.evvolitm.domain.repository.SearchProductRepository
import com.example.evvolitm.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository,
    private val searchProductRepository: SearchProductRepository,
    private val productDetailRepository: ProductDetailRepository,
    private val cartRepository: CartRepository,
    private val orderRepository: OrderRepository,
): AndroidViewModel(application) {
    private val _categoryScreenState = MutableStateFlow(CategoryScreenState())
    val categoryScreenState = _categoryScreenState.asStateFlow()

    private val _productScreenState = MutableStateFlow(ProductScreenState())
    val productScreenState = _productScreenState.asStateFlow()

    private val _searchProductScreenState = MutableStateFlow(ProductScreenState())
    val searchProductScreenState = _searchProductScreenState.asStateFlow()

    private val _productDetailScreenState = MutableStateFlow(ProductDetailScreenState())
    val productDetailScreenState = _productDetailScreenState.asStateFlow()

    private val _cartScreenState = MutableStateFlow(CartScreenState())
    val cartScreenState = _cartScreenState.asStateFlow()

    private val _orderStatus = MutableStateFlow<OrderStatus>(OrderStatus.Idle)
    val orderStatus = _orderStatus.asStateFlow()

    var productCategoryId: String? = null

    init {
        loadCategories(forceFetchFromRemote = false)
    }

    // -----------------------------STATE EVENTS LOGIC ------------------------------------

    fun onCategoryScreenEvent(event: CategoryScreenEvents) {
        when (event) {
            is CategoryScreenEvents.Refresh -> {
                _categoryScreenState.update { currentState ->
                    currentState.copy(
                        isLoading = true,
                        page = 1,
                        hasError = false,
                        categoryList = emptyList(),
                    )
                }
                loadCategories(forceFetchFromRemote = true)
            }

            is CategoryScreenEvents.OnPaginate -> {
                _categoryScreenState.value.page++
                loadCategories(forceFetchFromRemote = true)
            }

        }
    }

    fun onProductScreenEvent(event: ProductScreenEvents, categoryId: String) {
        when (event) {
            is ProductScreenEvents.Refresh -> {
                _productScreenState.update { currentState ->
                    currentState.copy(
                        isLoading = true,
                        page = 1,
                        hasError = false,
                        productList = emptyList(),
                    )
                }
                loadCategoryProducts(categoryId = categoryId, forceFetchFromRemote = true)
            }

            is ProductScreenEvents.OnPaginate -> {
                productScreenState.value.page++
                loadCategoryProducts(categoryId = categoryId, forceFetchFromRemote = true)
            }

        }
    }

    fun onSearchProductScreenEvent(event: ProductScreenEvents, query: String) {
        when (event) {
            is ProductScreenEvents.Refresh -> {
                loadSearchProducts(q = query, forceFetchFromRemote = true)
            }

            is ProductScreenEvents.OnPaginate -> {
                searchProductScreenState.value.page++
                loadSearchProducts(q = query, forceFetchFromRemote = true)
            }

        }
    }

    fun onProductDetailScreenEvent(event: ProductDetailScreenEvents, productId: String) {
        when (event) {
            is ProductDetailScreenEvents.Refresh -> {
                loadProductDetail(productId = productId, forceFetchFromRemote = true)
            }
        }
    }

    // -----------------------------CATEGORIES LOGIC ------------------------------------

    private fun loadCategories(
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {

            if (_cartScreenState.value.id == null) {
                createCartScreenState()
            }

            _categoryScreenState.update {
                it.copy(isLoading = true)
            }

            categoryRepository.getCategories(
                fetchFromRemote = forceFetchFromRemote,
                isRefresh = isRefresh,
                page = categoryScreenState.value.page
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        _categoryScreenState.update { currentState ->
                            currentState.copy(isLoading = false, hasError = true)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { categoryList ->
                            _categoryScreenState.update {
                                it.copy(
                                    categoryList = categoryScreenState.value.categoryList
                                            + categoryList,
                                    hasError = false
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _categoryScreenState.update {
                            it.copy(
                                isLoading = result.isLoading,
                                hasError = false
                            )
                        }
                    }
                }
            }
        }
    }

    // -----------------------------PRODUCTS LOGIC ------------------------------------

    fun loadCategoryProducts(
        categoryId: String,
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {

            if (_cartScreenState.value.id == null) {
                createCartScreenState()
            }

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
                    is Resource.Error -> {
                        _productScreenState.update { currentState ->
                            currentState.copy(isLoading = false, hasError = true)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { productList ->
                            _productScreenState.update {
                                it.copy(
                                    productList = productScreenState.value.productList
                                            + productList,
                                    hasError = false,
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _productScreenState.update {
                            it.copy(isLoading = result.isLoading, hasError = false)
                        }
                    }
                }
            }
        }
    }

    // -----------------------------SEARCH (PRODUCTS) LOGIC ------------------------------------

    fun loadSearchProducts(
        q: String = "",
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {

            if (_cartScreenState.value.id == null) {
                createCartScreenState()
            }

            _searchProductScreenState.update {
                it.copy(isLoading = true)
            }

            searchProductRepository.getFoundProducts(
                fetchFromRemote = forceFetchFromRemote,
                isRefresh = isRefresh,
                q = q,
                page = searchProductScreenState.value.page
            ).collect { result ->
                when (result) {
                    is Resource.Error -> Unit
                    is Resource.Success -> {

                        result.data?.let { productList ->
                            _searchProductScreenState.update {
                                it.copy(
                                    query = q,
                                    productList = searchProductScreenState.value.productList
                                            + productList
                                )
                            }
                        }
                    }

                    is Resource.Loading -> {
                        _searchProductScreenState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

    // -----------------------------DETAIL (PRODUCT) LOGIC ------------------------------------

    fun loadProductDetail(
        productId: String,
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {

            if (_cartScreenState.value.id == null) {
                createCartScreenState()
            }

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
                        result.data?.let { productDetail ->
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


    // -----------------------------CART LOGIC ------------------------------------


    fun updateCart(
        cartItemProduct: CartItemProduct,
        isMinus: Boolean = false
    ) {
        viewModelScope.launch {
            val quantity = if (isMinus) -1 else 1

            var cartId = _cartScreenState.value.id
            if (cartId == null) {
                createCartScreenState()
                cartId = _cartScreenState.value.id
            }

            if (cartId != null) {
                cartRepository.addOrUpdateCartItemEntity(
                    cartId = cartId,
                    cartItemProduct = cartItemProduct,
                    quantity = quantity
                )

                val cartWithItemsAndProducts = cartRepository.getCartWithItemsAndProducts(cartId)
                val cart = cartWithItemsAndProducts.cart
                val cartItems = cartWithItemsAndProducts.cartItems

                // Create a new instance with the updated values
                val newCartState = _cartScreenState.value.copy(
                    id = cart.id,
                    cartItems = cartItems,
                    cartQty = cartItems.sumOf { item -> item.cartItem.quantity },
                    cartTotalPrice = cartItems
                        .sumOf { item ->
                            item.cartItem.quantity * (item.product.salePrice ?: 0.00)
                        }
                )

                // Update the state
                _cartScreenState.value = newCartState
            }
        }
    }


    fun createCartScreenState() {
        viewModelScope.launch {
            val cartEntity =
                cartRepository.getLatestCartEntity() ?: cartRepository.createEmptyCartEntity()
            cartEntity?.let {
                val newCartWithItemsAndProducts = cartRepository.getCartWithItemsAndProducts(it.id)
                val newCart = newCartWithItemsAndProducts.cart
                val cartItems = newCartWithItemsAndProducts.cartItems
                newCart.let { cart ->
                    _cartScreenState.value = _cartScreenState.value.copy(
                        id = cart.id,
                        cartItems = cartItems,
                        cartQty = cartItems.sumOf { item -> item.cartItem.quantity },
                        cartTotalPrice = cartItems
                            .sumOf { item ->
                                item.cartItem.quantity * (item.product.salePrice ?: 0.00)
                            }
                    )
                }
            }
        }
    }

    private fun deleteAllCarts() {
        viewModelScope.launch {
            cartRepository.deleteAllCarts()
        }
    }

    // -----------------------------ORDER LOGIC ------------------------------------

    fun createOrder(orderDto: OrderDto) {
        viewModelScope.launch {
            val response = orderRepository.createOrder(orderDto)

            if (response.isSuccessful) {
                // Order creation successful, navigate to success page
                _orderStatus.value = OrderStatus.Success("Order Placed Successfully")
                deleteAllCarts()
                return@launch
            } else {
                val errorResponse = response.errorBody()?.string()
                val errorMessage = parseError(errorResponse)

                if (errorMessage == null) {
                    _orderStatus.value = OrderStatus.Error( "Failed to place order. Please try again.")
                    return@launch
                }

                val detail = errorMessage.first
                val productId = errorMessage.second

                if (detail == null || productId == null) {
                    _orderStatus.value = OrderStatus.Error( "Failed to place order. Please try again.")
                    return@launch
                } else {
                    try {
                        val cartId = _cartScreenState.value.id ?: cartRepository.getLatestCartEntity()?.id
                        if (cartId == null) {
                            _orderStatus.value = OrderStatus.Error(
                                "Nothing was added to cart, Please add product to your cart first!"
                            )
                            return@launch
                        }

                        val cartEntity = cartRepository.getCartById(id = cartId)
                        if (cartEntity == null) {
                            _orderStatus.value = OrderStatus.Error(
                                "No cart with given id was found"
                            )
                            return@launch
                        }

                        // Get CartItemEntity
                        val cartItemEntity = cartRepository.getCartItemByProductIdAndCartId(productId = productId, cartId = cartId)
                        if (cartItemEntity == null) {
                            _orderStatus.value = OrderStatus.Error(
                                "No such cart item!"
                            )
                            return@launch
                        }

                        // Delete CartItemEntity
                        cartRepository.deleteCartItem(cartItemId = cartItemEntity.id)

                        // Delete CartItemProductEntity
                        cartRepository.deleteCartItemProductById(productId = productId)

                        // Re-fetch the CartEntity
                        val updateCartEntityWithCartItemProduct = cartRepository.getCartWithItemsAndProducts(cartId = cartId)
                        val updateCartEntity = updateCartEntityWithCartItemProduct.cart

                        val cartItems = updateCartEntityWithCartItemProduct.cartItems

                        // Update cartState UI
                        updateCartEntity.let { cart ->
                            _cartScreenState.value = _cartScreenState.value.copy(
                                id = cart.id,
                                cartItems = cartItems,
                                cartQty = cartItems.sumOf { item -> item.cartItem.quantity },
                                cartTotalPrice = cartItems
                                    .sumOf { item ->
                                        item.cartItem.quantity * (item.product.salePrice ?: 0.00)
                                    }
                            )
                        }
                        _orderStatus.value = OrderStatus.Error(
                            "Product in your cart are no longer available for purchase. It will be removed from your cart!"
                        )
                    } catch(e: Exception)  {
                        _orderStatus.value = OrderStatus.Error(
                            "Unexpected error occurred!"
                        )
                    }
                }
            }
        }
    }

    fun resetOrderStatusAndCartState() {
        _orderStatus.value = OrderStatus.Idle
        _cartScreenState.update {
            it.copy(
                id = null,
                isLoading = false,
                cartItems = mutableListOf(),
                cartQty = 0,
                cartTotalPrice = 0.00
            )
        }
    }

    private fun parseError(response: String?): Pair<String?, String?>? {
        response?.let {
            try {
                val json = JSONObject(it)
                val detail = json.getString("detail")
                val productId = json.getString("product_id")
                return Pair(detail, productId)
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return null
    }
}