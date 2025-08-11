package com.nnzapp.easymarket.data.repository

import com.nnzapp.easymarket.data.error.AppException
import com.nnzapp.easymarket.data.model.OrderRequestDto
import com.nnzapp.easymarket.data.remote.ApiService
import com.nnzapp.easymarket.domain.model.CartItem
import com.nnzapp.easymarket.domain.model.Order
import com.nnzapp.easymarket.domain.model.Product
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class OrderRepositoryImplTest {
    private val mockApiService =
        object : ApiService {
            var shouldThrowException = false
            var exceptionToThrow: Throwable? = null
            var orderResult = true
            var lastOrderRequestDto: OrderRequestDto? = null

            override suspend fun getProducts(): List<com.nnzapp.easymarket.data.model.ProductDto> = throw NotImplementedError()

            override suspend fun getStoreInfo(): com.nnzapp.easymarket.data.model.StoreDto = throw NotImplementedError()

            override suspend fun placeOrder(orderRequest: OrderRequestDto): Boolean {
                if (shouldThrowException) {
                    throw exceptionToThrow ?: RuntimeException("Test exception")
                }
                lastOrderRequestDto = orderRequest
                return orderResult
            }
        }

    private val repository = OrderRepositoryImpl(mockApiService)

    @Test
    fun `placeOrder returns success when api call succeeds`() =
        runBlocking {
            // Given
            val product = Product(name = "Test Product", price = 100, imageUrl = "test.jpg")
            val cartItem = CartItem(product = product, quantity = 2.0)
            val order = Order(items = listOf(cartItem), deliveryAddress = "123 Test St")

            mockApiService.orderResult = true
            mockApiService.shouldThrowException = false

            // When
            val result = repository.placeOrder(order)

            // Then
            assertTrue(result.isSuccess)
            assertEquals(true, result.getOrNull())
        }

    @Test
    fun `placeOrder returns failure when api call throws exception`() =
        runBlocking {
            // Given
            val product = Product(name = "Test Product", price = 100, imageUrl = "test.jpg")
            val cartItem = CartItem(product = product, quantity = 2.0)
            val order = Order(items = listOf(cartItem), deliveryAddress = "123 Test St")

            val testException = RuntimeException("Network error")
            mockApiService.shouldThrowException = true
            mockApiService.exceptionToThrow = testException

            // When
            val result = repository.placeOrder(order)

            // Then
            assertTrue(result.isFailure)
            val exception = result.exceptionOrNull()
            assertTrue(exception is AppException)
        }

    @Test
    fun `placeOrder returns false when api returns false`() =
        runBlocking {
            // Given
            val product = Product(name = "Test Product", price = 100, imageUrl = "test.jpg")
            val cartItem = CartItem(product = product, quantity = 1.0)
            val order = Order(items = listOf(cartItem), deliveryAddress = "456 Test Ave")

            mockApiService.orderResult = false
            mockApiService.shouldThrowException = false

            // When
            val result = repository.placeOrder(order)

            // Then
            assertTrue(result.isSuccess)
            assertEquals(false, result.getOrNull())
        }

    @Test
    fun `placeOrder converts domain order to dto correctly`() =
        runBlocking {
            // Given
            val product = Product(name = "Conversion Test", price = 250, imageUrl = "conv.jpg")
            val cartItem = CartItem(product = product, quantity = 3.0)
            val order = Order(items = listOf(cartItem), deliveryAddress = "789 Conv St")

            mockApiService.orderResult = true
            mockApiService.shouldThrowException = false

            // When
            repository.placeOrder(order)

            // Then
            val capturedDto = mockApiService.lastOrderRequestDto!!
            assertEquals(order.deliveryAddress, capturedDto.deliveryAddress)
            assertEquals(3, capturedDto.products.size) // quantity of 3.0 becomes 3 individual products
            assertEquals(product.name, capturedDto.products[0].name)
            assertEquals(product.price, capturedDto.products[0].price)
            // All products should be the same
            capturedDto.products.forEach { productDto ->
                assertEquals(product.name, productDto.name)
                assertEquals(product.price, productDto.price)
            }
        }
}
