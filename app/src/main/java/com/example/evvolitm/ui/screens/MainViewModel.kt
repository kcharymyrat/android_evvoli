package com.example.evvolitm.ui.screens

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.evvolitm.EvvoliTmApplication
import com.example.evvolitm.data.EvvoliTmApiRepository
import com.example.evvolitm.model.Category
import com.example.evvolitm.model.Product
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for the Categories screen
 */
sealed interface CategoriesUiState {
    data class Success(val categories: List<Category>) : CategoriesUiState
    object Error : CategoriesUiState
    object Loading : CategoriesUiState
}

sealed interface ProductsUiState {
    data class Success(val products: List<Product>) : ProductsUiState
    object Error : ProductsUiState
    object Loading : ProductsUiState
}

class MainViewModel(private val evvoliTmApiRepository: EvvoliTmApiRepository): ViewModel() {
    /** The mutable State that stores the status of the most recent request */
    var categoriesUiState: CategoriesUiState by mutableStateOf(CategoriesUiState.Loading)
        private set

    var productsUiState: ProductsUiState by mutableStateOf(ProductsUiState.Loading)
        private set

    init {
        getCategories()
    }

    fun getCategories() {
        Log.i("Main", "in getCategories")
        viewModelScope.launch {
            Log.i("Main", "in viewModelScope.launch")
            categoriesUiState = CategoriesUiState.Loading
            Log.i("Main", "categoriesUiState =$categoriesUiState")
            categoriesUiState = try {
                CategoriesUiState.Success(evvoliTmApiRepository.getCategories().results)
            } catch (e: IOException) {
                CategoriesUiState.Error
            } catch (e: HttpException) {
                CategoriesUiState.Error
            }
        }
    }

    fun getCategoryProducts(categorySlug: String) {
        viewModelScope.launch {
            productsUiState = ProductsUiState.Loading
            productsUiState = try {
                ProductsUiState.Success(evvoliTmApiRepository.getCategoryProducts(categorySlug).results)
            } catch (e: IOException) {
                ProductsUiState.Error
            } catch (e: HttpException) {
                ProductsUiState.Error
            }
        }
    }

    /**
     * Factory for [MainViewModel] that takes [EvvoliTmApiRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as EvvoliTmApplication)
                val evvoliTmApiRepository = application.container.evvoliTmApiRepository
                MainViewModel(evvoliTmApiRepository = evvoliTmApiRepository)
            }
        }
    }
}