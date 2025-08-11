package com.nnzapp.easymarket.domain.usecase

import com.nnzapp.easymarket.domain.model.CartItem
import com.nnzapp.easymarket.domain.model.Order
import com.nnzapp.easymarket.domain.repository.OrderRepository

class PlaceOrderUseCase(
    private val orderRepository: OrderRepository,
) {
    suspend operator fun invoke(
        cartItems: List<CartItem>,
        deliveryAddress: String,
    ): Boolean {
        require(cartItems.isNotEmpty()) { "Cart is empty" }
        require(deliveryAddress.isNotBlank()) { "Delivery address is required" }

        val order =
            Order(
                items = cartItems,
                deliveryAddress = deliveryAddress,
            )

        return orderRepository.placeOrder(order).getOrThrow()
    }
}
