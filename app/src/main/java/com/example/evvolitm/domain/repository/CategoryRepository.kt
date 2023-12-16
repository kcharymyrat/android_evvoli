package com.example.evvolitm.domain.repository

import com.example.evvolitm.domain.model.Category
import com.example.evvolitm.util.Resource
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun updateCategoryItem(category: Category)

    suspend fun insertCategoryItem(category: Category)

    suspend fun getCategory(id: String): Category

    suspend fun getCategories(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        page: Int,
    ): Flow<Resource<List<Category>>>

}