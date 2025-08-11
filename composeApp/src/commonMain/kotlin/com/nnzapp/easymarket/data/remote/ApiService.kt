package com.nnzapp.easymarket.data.remote

import com.nnzapp.easymarket.data.model.OrderRequestDto
import com.nnzapp.easymarket.data.model.ProductDto
import com.nnzapp.easymarket.data.model.StoreDto

interface ApiService {
    suspend fun getStoreInfo(): StoreDto

    suspend fun getProducts(): List<ProductDto>

    suspend fun placeOrder(orderRequest: OrderRequestDto): Boolean
}
