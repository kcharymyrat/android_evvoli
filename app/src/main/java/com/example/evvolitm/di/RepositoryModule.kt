package com.example.evvolitm.di

import com.example.evvolitm.data.repository.CategoryRepositoryImpl
import com.example.evvolitm.data.repository.ProductRepositoryImpl
import com.example.evvolitm.domain.repository.CategoryRepository
import com.example.evvolitm.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCategoryRepository(
        categoryRepositoryImpl: CategoryRepositoryImpl
    ): CategoryRepository


    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ): ProductRepository

}