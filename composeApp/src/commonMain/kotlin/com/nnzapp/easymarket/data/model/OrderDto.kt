package com.nnzapp.easymarket.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderRequestDto(
    val products: List<ProductDto>,
    @SerialName("delivery_address")
    val deliveryAddress: String,
)
