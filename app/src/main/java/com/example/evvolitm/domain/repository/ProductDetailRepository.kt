package com.example.evvolitm.domain.repository

import com.example.evvolitm.domain.model.Product
import com.example.evvolitm.domain.model.ProductDetail
import com.example.evvolitm.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProductDetailRepository {
    suspend fun getProductDetail(
        productId: String,
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
    ): Flow<Resource<ProductDetail>>
}