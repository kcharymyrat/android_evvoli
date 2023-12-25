package com.example.evvolitm.domain.repository

import com.example.evvolitm.data.remote.respond.order_dtos.OrderDto
import retrofit2.Response

interface OrderRepository {
    suspend fun createOrder(orderDto: OrderDto): Response<Unit>
}
