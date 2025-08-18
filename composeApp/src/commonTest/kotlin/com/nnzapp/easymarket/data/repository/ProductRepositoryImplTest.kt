package com.nnzapp.easymarket.data.repository

import com.nnzapp.easymarket.data.error.AppException
import com.nnzapp.easymarket.data.model.ProductDto
import com.nnzapp.easymarket.data.remote.ApiService
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProductRepositoryImplTest {
    private val mockApiService =
        object : ApiService {
            var shouldThrowException = false
            var exceptionToThrow: Throwable? = null
            var productsToReturn: List<ProductDto> = emptyList()

            override suspend fun getProducts(): List<ProductDto> {
                if (shouldThrowException) {
                    throw exceptionToThrow ?: RuntimeException("Test exception")
                }
                return productsToReturn
            }

            override suspend fun getStoreInfo(): com.nnzapp.easymarket.data.model.StoreDto =
                throw NotImplementedError()

            override suspend fun placeOrder(orderRequest: com.nnzapp.easymarket.data.model.OrderRequestDto): Boolean =
                throw NotImplementedError()
        }

    private val repository = ProductRepositoryImpl(mockApiService)

    @Test
    fun `getProducts returns success when api call succeeds`() =
        runBlocking {
            // Given
            val expectedProductsDto =
                listOf(
                    ProductDto(id = "1", name = "Product 1", price = 100, imageUrl = "image1.jpg"),
                    ProductDto(id = "2", name = "Product 2", price = 200, imageUrl = "image2.jpg"),
                )
            mockApiService.productsToReturn = expectedProductsDto
            mockApiService.shouldThrowException = false

            // When
            val result = repository.getProducts()

            // Then
            assertTrue(result.isSuccess)
            val products = result.getOrNull()!!
            assertEquals(2, products.size)
            assertEquals("Product 1", products[0].name)
            assertEquals(100, products[0].price)
            assertEquals("image1.jpg", products[0].imageUrl)
            assertEquals("Product 2", products[1].name)
            assertEquals(200, products[1].price)
            assertEquals("image2.jpg", products[1].imageUrl)
        }

    @Test
    fun `getProducts returns failure when api call throws exception`() =
        runBlocking {
            // Given
            val testException = RuntimeException("Network error")
            mockApiService.shouldThrowException = true
            mockApiService.exceptionToThrow = testException

            // When
            val result = repository.getProducts()

            // Then
            assertTrue(result.isFailure)
            val exception = result.exceptionOrNull()
            assertTrue(exception is AppException)
        }

    @Test
    fun `getProducts returns empty list when api returns empty list`() =
        runBlocking {
            // Given
            mockApiService.productsToReturn = emptyList()
            mockApiService.shouldThrowException = false

            // When
            val result = repository.getProducts()

            // Then
            assertTrue(result.isSuccess)
            val products = result.getOrNull()!!
            assertTrue(products.isEmpty())
        }

    @Test
    fun `getProducts maps dto to domain correctly`() =
        runBlocking {
            // Given
            val productDto =
                ProductDto(
                    id= "1",
                    name = "Test Product",
                    price = 150,
                    imageUrl = "test.jpg",
                )
            mockApiService.productsToReturn = listOf(productDto)
            mockApiService.shouldThrowException = false

            // When
            val result = repository.getProducts()

            // Then
            assertTrue(result.isSuccess)
            val product = result.getOrNull()!!.first()
            assertEquals(productDto.name, product.name)
            assertEquals(productDto.price, product.price)
            assertEquals(productDto.imageUrl, product.imageUrl)
            assertEquals(productDto.name.hashCode().toString(), product.id)
        }
}
