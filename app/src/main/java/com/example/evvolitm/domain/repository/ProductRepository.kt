package com.example.evvolitm.domain.repository

import com.example.evvolitm.domain.model.Product
import com.example.evvolitm.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(
        categoryId: String,
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        page: Int,
    ): Flow<Resource<List<Product>>>

}
