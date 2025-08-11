package com.nnzapp.easymarket.data.model

import kotlinx.serialization.Serializable

@Serializable
data class StoreDto(
    val name: String,
    val rating: Double,
    val openingTime: String,
    val closingTime: String
)