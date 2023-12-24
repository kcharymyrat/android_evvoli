package com.example.evvolitm.data.remote

import com.example.evvolitm.data.remote.respond.category_dtos.CategoryDto
import com.example.evvolitm.data.remote.respond.category_dtos.CategoriesResponseDto
import com.example.evvolitm.data.remote.respond.product_dtos.ProductDetailDto
import com.example.evvolitm.data.remote.respond.product_dtos.ProductsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EvvoliTmApi {
    @GET("api/v1/categories/")
    suspend fun getCategories(@Query("page") page: Int): CategoriesResponseDto

    @GET("api/v1/categories/{category_slug}/")
    suspend fun getCategory(@Path("category_slug") categorySlug: String): CategoryDto

    @GET("api/v1/categories/{category_id}/products/")
    suspend fun getCategoryProductList(
        @Path("category_id") categoryId: String,
        @Query("page") page: Int
    ): ProductsResponseDto

    @GET("api/v1/search/")
    suspend fun getProductSearchList(
        @Query("q") q: String,
        @Query("page") page: Int
    ): ProductsResponseDto


    @GET("api/v1/products/{product_id}/")
    suspend fun getProductDetail(
        @Path("product_id") productId: String,
    ): ProductDetailDto

    companion object {
        const val BASE_URL = "http://192.168.1.14:8000/"
        const val IMAGE_BASE_URL = "http://192.168.1.14:8000/"
    }
}