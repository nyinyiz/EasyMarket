package com.nnzapp.easymarket.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: String,
    val name: String,
    val price: Int,
    val imageUrl: String,
)

@Serializable
data class PaginationInfo(
    val total_count: Int,
    val current_page: Int,
    val total_pages: Int,
)

@Serializable
data class ProductResult(
    val PaginationInfo: PaginationInfo,
    val Products: List<ProductDto>,
)

@Serializable
data class ProductData(
    val ProductResult: ProductResult,
)

@Serializable
data class ProductResponseDto(
    val data: ProductData,
)
