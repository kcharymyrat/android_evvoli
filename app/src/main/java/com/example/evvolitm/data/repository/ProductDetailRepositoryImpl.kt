package com.example.evvolitm.data.repository

import com.example.evvolitm.data.remote.EvvoliTmApi
import com.example.evvolitm.data.remote.respond.product_dtos.ProductDetailDto
import com.example.evvolitm.data.remote.respond.product_dtos.ProductDto
import com.example.evvolitm.domain.model.Product
import com.example.evvolitm.domain.model.ProductDetail
import com.example.evvolitm.domain.repository.ProductDetailRepository
import com.example.evvolitm.mappers.toProduct
import com.example.evvolitm.mappers.toProductDetail
import com.example.evvolitm.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductDetailRepositoryImpl @Inject constructor(
    private val evvoliTmApi: EvvoliTmApi,
): ProductDetailRepository {

    override suspend fun getProductDetail(
        productId: String,
        fetchFromRemote: Boolean,
        isRefresh: Boolean
    ): Flow<Resource<ProductDetail>> {
        return flow {
            emit(Resource.Loading(true))

            val remoteProductDetailDto: ProductDetailDto? = try {
                evvoliTmApi.getProductDetail(productId = productId)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load product"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load product"))
                null
            }

            remoteProductDetailDto?.let {
                val productDetail: ProductDetail = remoteProductDetailDto.toProductDetail()
                emit(Resource.Success(data = productDetail))
                emit(Resource.Loading(false))
            }

        }
    }
}
