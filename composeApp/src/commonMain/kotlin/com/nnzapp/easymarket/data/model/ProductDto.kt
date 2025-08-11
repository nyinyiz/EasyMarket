package com.nnzapp.easymarket.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val name: String,
    val price: Int,
    val imageUrl: String,
)
