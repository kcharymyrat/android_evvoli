package com.example.evvolitm.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.evvolitm.domain.repository.CategoryRepository
import com.example.evvolitm.domain.repository.ProductRepository
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
    private val productRepository: ProductRepository
): AndroidViewModel(application) {
    private val _categoryScreenState = MutableStateFlow(CategoryScreenState())
    val categoryScreenState = _categoryScreenState.asStateFlow()

    private val _productScreenState = MutableStateFlow(ProductScreenState())
    val productScreenState = _productScreenState.asStateFlow()

    init {
        loadCategories(forceFetchFromRemote = false)
    }

    private fun load(fetchFromRemote: Boolean = false) {
        loadCategories(fetchFromRemote)
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

    private fun loadCategories(
        forceFetchFromRemote: Boolean = false,
        isRefresh: Boolean = false
    ) {
        viewModelScope.launch {
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

}