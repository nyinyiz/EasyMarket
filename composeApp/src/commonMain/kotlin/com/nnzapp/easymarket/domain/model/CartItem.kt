package com.nnzapp.easymarket.domain.model

data class CartItem(
    val product: Product,
    val quantity: Int
) {
    val totalPrice: Int
        get() = product.price * quantity
}