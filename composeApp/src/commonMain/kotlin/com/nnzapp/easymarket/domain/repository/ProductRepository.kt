package com.nnzapp.easymarket.domain.repository

import com.nnzapp.easymarket.domain.model.Product

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
}
