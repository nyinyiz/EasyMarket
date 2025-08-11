package com.nnzapp.easymarket.data.repository

import com.nnzapp.easymarket.data.error.toAppException
import com.nnzapp.easymarket.data.mapper.toDto
import com.nnzapp.easymarket.data.remote.ApiService
import com.nnzapp.easymarket.domain.model.Order
import com.nnzapp.easymarket.domain.repository.OrderRepository

class OrderRepositoryImpl(
    private val apiService: ApiService,
) : OrderRepository {
    override suspend fun placeOrder(order: Order): Result<Boolean> =
        try {
            val success = apiService.placeOrder(order.toDto())
            Result.success(success)
        } catch (t: Throwable) {
            Result.failure(t.toAppException())
        }
}
