package com.example.evvolitm.data


import com.example.evvolitm.model.CategoryResponse
import com.example.evvolitm.model.ProductResponse
import com.example.evvolitm.network.EvvoliTmApiService
import retrofit2.http.Path

interface EvvoliTmApiRepository {
    suspend fun getCategories(page:Int): CategoryResponse
    suspend fun getCategoryProducts(categorySlug: String): ProductResponse
}

/**
 * Network Implementation of EvvoliTmApiRepository that fetch data from Api.
 */
class NetworkEvvoliTmApiRepository(
    private val evvoliTmApiService: EvvoliTmApiService
) : EvvoliTmApiRepository {
    override suspend fun getCategories(page: Int): CategoryResponse =
        evvoliTmApiService.getCategories(page)

    override suspend fun getCategoryProducts(categorySlug: String): ProductResponse =
        evvoliTmApiService.getCategoryProducts(categorySlug)
}