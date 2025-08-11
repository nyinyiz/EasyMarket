package com.nnzapp.easymarket.domain.model

data class CartItem(
    val product: Product,
    val quantity: Double,
) {
    val totalPrice: Double
        get() = product.price * quantity
}
