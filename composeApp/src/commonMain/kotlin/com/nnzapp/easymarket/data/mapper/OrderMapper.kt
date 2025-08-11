package com.nnzapp.easymarket.data.mapper

import com.nnzapp.easymarket.data.model.OrderRequestDto
import com.nnzapp.easymarket.domain.model.Order

fun Order.toDto(): OrderRequestDto {
    val products = mutableListOf<com.nnzapp.easymarket.data.model.ProductDto>()
    
    items.forEach { cartItem ->
        repeat(cartItem.quantity) {
            products.add(cartItem.product.toDto())
        }
    }
    
    return OrderRequestDto(
        products = products,
        deliveryAddress = deliveryAddress
    )
}