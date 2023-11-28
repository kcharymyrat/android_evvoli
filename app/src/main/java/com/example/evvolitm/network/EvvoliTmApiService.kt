package com.example.evvolitm.network

import retrofit2.http.GET
import retrofit2.http.Path
import com.example.evvolitm.model.CategoryResponse
import com.example.evvolitm.model.ProductResponse


interface EvvoliTmApiService {
    @GET("api/v1/categories/")
    suspend fun getCategories(): CategoryResponse

    @GET("api/v1/categories/{category_slug}/products/")
    suspend fun getCategoryProducts(@Path("category_slug") categorySlug: String): ProductResponse
}
