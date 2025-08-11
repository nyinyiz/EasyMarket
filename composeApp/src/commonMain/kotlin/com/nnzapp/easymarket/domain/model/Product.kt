package com.nnzapp.easymarket.domain.model

data class Product(
    val name: String,
    val price: Int,
    val imageUrl: String
) {
    val id: String get() = name.hashCode().toString()
}