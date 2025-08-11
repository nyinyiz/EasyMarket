package com.nnzapp.easymarket.domain.model

data class Order(
    val items: List<CartItem>,
    val deliveryAddress: String
) {
    val totalAmount: Int
        get() = items.sumOf { it.totalPrice }
}