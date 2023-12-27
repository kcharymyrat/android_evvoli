package com.example.evvolitm.di

import com.example.evvolitm.data.local.cart.CartItemDao
import com.example.evvolitm.data.repository.CartRepositoryImpl
import com.example.evvolitm.data.repository.CategoryRepositoryImpl
import com.example.evvolitm.data.repository.OrderRepositoryImpl
import com.example.evvolitm.data.repository.ProductDetailRepositoryImpl
import com.example.evvolitm.data.repository.ProductRepositoryImpl
import com.example.evvolitm.data.repository.SearchProductRepositoryImpl
import com.example.evvolitm.domain.repository.CartRepository
import com.example.evvolitm.domain.repository.CategoryRepository
import com.example.evvolitm.domain.repository.OrderRepository
import com.example.evvolitm.domain.repository.ProductDetailRepository
import com.example.evvolitm.domain.repository.ProductRepository
import com.example.evvolitm.domain.repository.SearchProductRepository
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

    @Binds
    @Singleton
    abstract fun bindSearchProductRepository(
        searchProductRepositoryImpl: SearchProductRepositoryImpl
    ): SearchProductRepository


    @Binds
    @Singleton
    abstract fun bindProductDetailRepository(
        productDetailRepositoryImpl: ProductDetailRepositoryImpl
    ): ProductDetailRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepositoryImpl: CartRepositoryImpl
    ): CartRepository


    @Binds
    @Singleton
    abstract fun bindOrderRepository(
        orderRepositoryImpl: OrderRepositoryImpl
    ): OrderRepository
}