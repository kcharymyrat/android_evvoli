package com.example.evvolitm.data.repository

import com.example.evvolitm.data.remote.EvvoliTmApi
import com.example.evvolitm.data.remote.respond.product_dtos.ProductDto
import com.example.evvolitm.domain.model.Category
import com.example.evvolitm.domain.model.Product
import com.example.evvolitm.domain.repository.ProductRepository
import com.example.evvolitm.domain.repository.SearchProductRepository
import com.example.evvolitm.mappers.toCategory
import com.example.evvolitm.mappers.toProduct
import com.example.evvolitm.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchProductRepositoryImpl  @Inject constructor(
    private val evvoliTmApi: EvvoliTmApi,
): SearchProductRepository {

    override suspend fun getFoundProducts(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        q: String,
        page: Int
    ): Flow<Resource<List<Product>>> {
        return flow {
            emit(Resource.Loading(true))

            var currentPage = page
            if (isRefresh) {
                currentPage = 1
            }

            val remoteProductList: List<ProductDto>? = try {
                evvoliTmApi.getProductSearchList(q = q, page = currentPage).results
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load products"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load products"))
                null
            }

            remoteProductList?.let {
                val product: List<Product> = it.map { productDto ->
                    productDto.toProduct()
                }
                emit(Resource.Success(data = product))
                emit(Resource.Loading(false))
            }

        }
    }
}

