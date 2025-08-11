package com.nnzapp.easymarket.data.repository

import com.nnzapp.easymarket.data.error.toAppException
import com.nnzapp.easymarket.data.mapper.toDomain
import com.nnzapp.easymarket.data.remote.ApiService
import com.nnzapp.easymarket.domain.model.Product
import com.nnzapp.easymarket.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val apiService: ApiService,
) : ProductRepository {
    override suspend fun getProducts(): Result<List<Product>> =
        try {
            val productsDto = apiService.getProducts()
            Result.success(productsDto.map { it.toDomain() })
        } catch (t: Throwable) {
            Result.failure(t.toAppException())
        }
}
