package com.example.evvolitm.data.repository

import com.example.evvolitm.data.remote.EvvoliTmApi
import com.example.evvolitm.data.remote.respond.order_dtos.OrderDto
import com.example.evvolitm.domain.repository.OrderRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepositoryImpl @Inject constructor(
    private val evvoliTmApi: EvvoliTmApi,
): OrderRepository {
    override suspend fun createOrder(orderDto: OrderDto): Response<Unit> {
        return evvoliTmApi.createOrder(orderDto)
    }
}
