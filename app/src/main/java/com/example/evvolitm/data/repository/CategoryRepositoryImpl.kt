package com.example.evvolitm.data.repository

import androidx.compose.runtime.saveable.rememberSaveable
import com.example.evvolitm.data.local.EvvoliTmDatabase
import com.example.evvolitm.data.local.category.CategoryEntity
import com.example.evvolitm.data.remote.EvvoliTmApi
import com.example.evvolitm.data.remote.respond.category_dtos.CategoryDto
import com.example.evvolitm.domain.model.Category
import com.example.evvolitm.domain.repository.CategoryRepository
import com.example.evvolitm.mappers.toCategory
import com.example.evvolitm.mappers.toCategoryEntity
import com.example.evvolitm.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val evvoliTmApi: EvvoliTmApi,
    evvoliTmDb: EvvoliTmDatabase
): CategoryRepository {
    private val categoryDao = evvoliTmDb.categoryDao
    private var nextUrl: String? = null

    override suspend fun updateCategoryItem(category: Category) {
        categoryDao.updateCategoryItem(category.toCategoryEntity())
    }

    override suspend fun insertCategoryItem(category: Category) {
        categoryDao.insertCategoryItem(category.toCategoryEntity())
    }

    override suspend fun getCategory(id: String): Category? {
        return categoryDao.getCategoryById(id)?.toCategory()
    }

    override suspend fun getCategories(
        fetchFromRemote: Boolean,
        isRefresh: Boolean,
        page: Int
    ): Flow<Resource<List<Category>>> {
        return flow {
            emit(Resource.Loading(true))

//            val localCategoryList = categoryDao.getCategories()
//
//            val shouldLoadFromCache = !fetchFromRemote && !isRefresh && localCategoryList.isNotEmpty()
//            println("shouldLoadFromCache = $shouldLoadFromCache")
//
//            if (shouldLoadFromCache) {
//                emit(Resource.Success(data = localCategoryList.map { it.toCategory() }))
//                emit(Resource.Loading(false))
//                return@flow
//            }

            var currentPage = page
            if (isRefresh) {
//                categoryDao.deleteAllCategories()
                currentPage = 1
            }

            val remoteCategoryList: List<CategoryDto>? = try {
                if (nextUrl != null || page == 1) {
                    val categoriesResponse = evvoliTmApi.getCategories(page = currentPage)
                    nextUrl = categoriesResponse.next
                    categoriesResponse.results
                } else { null }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteCategoryList?.let {
                val category: List<Category> = it.map { categoryDto ->
                    categoryDto.toCategory()
                }
//                val entities: List<CategoryEntity> = it.map {categoryDto ->
//                    categoryDto.toCategoryEntity()
//                }
//                categoryDao.insertCategoryList(entities)
                emit(Resource.Success(data = category))
                emit(Resource.Loading(false))
            }

        }
    }

}