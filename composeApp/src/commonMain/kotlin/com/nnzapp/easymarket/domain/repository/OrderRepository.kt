package com.nnzapp.easymarket.domain.repository

import com.nnzapp.easymarket.domain.model.Order

interface OrderRepository {
    suspend fun placeOrder(order: Order): Result<Boolean>
}
