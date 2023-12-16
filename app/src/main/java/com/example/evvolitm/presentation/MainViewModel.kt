package com.example.evvolitm.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.evvolitm.EvvoliTmApplication
import com.example.evvolitm.domain.repository.CategoryRepository
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
    private val categoryRepository: CategoryRepository
): AndroidViewModel(application) {
    private val _categoryScreenState = MutableStateFlow(CategoryScreenState())
    val categoryScreenState = _categoryScreenState.asStateFlow()

    init {
        loadCategories(forceFetchFromRemote = false)
    }

    private fun load(fetchFromRemote: Boolean = false) {
        loadCategories(fetchFromRemote)
    }

    fun onEvent(event: CategoryScreenEvents) {
        println("event = $event")
        when(event) {
            is CategoryScreenEvents.Refresh -> {
                loadCategories(forceFetchFromRemote = true)
            }

            is CategoryScreenEvents.OnPaginate -> {
                categoryScreenState.value.page++
                loadCategories(forceFetchFromRemote = true)
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

}