package com.nnzapp.easymarket.domain.usecase

import com.nnzapp.easymarket.domain.model.Product
import com.nnzapp.easymarket.domain.repository.ProductRepository
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetProductsUseCaseTest {
    private val mockProductRepository =
        object : ProductRepository {
            var resultToReturn: Result<List<Product>> = Result.success(emptyList())

            override suspend fun getProducts(): Result<List<Product>> = resultToReturn
        }

    private val useCase = GetProductsUseCase(mockProductRepository)

    @Test
    fun `invoke returns success when repository returns success`() =
        runBlocking {
            // Given
            val expectedProducts =
                listOf(
                    Product(name = "Product 1", price = 100, imageUrl = "image1.jpg"),
                    Product(name = "Product 2", price = 200, imageUrl = "image2.jpg"),
                )
            mockProductRepository.resultToReturn = Result.success(expectedProducts)

            // When
            val result = useCase.invoke()

            // Then
            assertTrue(result.isSuccess)
            val products = result.getOrNull()!!
            assertEquals(2, products.size)
            assertEquals("Product 1", products[0].name)
            assertEquals("Product 2", products[1].name)
        }

    @Test
    fun `invoke returns failure when repository returns failure`() =
        runBlocking {
            // Given
            val expectedError = RuntimeException("Network error")
            mockProductRepository.resultToReturn = Result.failure(expectedError)

            // When
            val result = useCase.invoke()

            // Then
            assertTrue(result.isFailure)
            assertEquals(expectedError, result.exceptionOrNull())
        }

    @Test
    fun `invoke returns empty list when repository returns empty list`() =
        runBlocking {
            // Given
            mockProductRepository.resultToReturn = Result.success(emptyList())

            // When
            val result = useCase.invoke()

            // Then
            assertTrue(result.isSuccess)
            val products = result.getOrNull()!!
            assertTrue(products.isEmpty())
        }

    @Test
    fun `invoke delegates to product repository`() =
        runBlocking {
            // Given
            val testProducts =
                listOf(
                    Product(name = "Test Product", price = 150, imageUrl = "test.jpg"),
                )
            mockProductRepository.resultToReturn = Result.success(testProducts)

            // When
            val result = useCase()

            // Then
            assertTrue(result.isSuccess)
            assertEquals(testProducts, result.getOrNull())
        }
}
