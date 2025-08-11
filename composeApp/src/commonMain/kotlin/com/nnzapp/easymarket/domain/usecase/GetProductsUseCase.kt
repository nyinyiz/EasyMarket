package com.nnzapp.easymarket.domain.usecase

import com.nnzapp.easymarket.domain.repository.ProductRepository

class GetProductsUseCase(
    private val productRepository: ProductRepository,
) {
    suspend operator fun invoke() = productRepository.getProducts()
}
