package com.nnzapp.easymarket.data.repository

import com.nnzapp.easymarket.data.error.AppException
import com.nnzapp.easymarket.data.model.StoreDto
import com.nnzapp.easymarket.data.remote.ApiService
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class StoreRepositoryImplTest {

    private val mockApiService = object : ApiService {
        var shouldThrowException = false
        var exceptionToThrow: Throwable? = null
        var storeToReturn: StoreDto? = null

        override suspend fun getProducts(): List<com.nnzapp.easymarket.data.model.ProductDto> {
            throw NotImplementedError()
        }

        override suspend fun getStoreInfo(): StoreDto {
            if (shouldThrowException) {
                throw exceptionToThrow ?: RuntimeException("Test exception")
            }
            return storeToReturn ?: throw RuntimeException("No store data configured")
        }

        override suspend fun placeOrder(orderRequest: com.nnzapp.easymarket.data.model.OrderRequestDto): Boolean {
            throw NotImplementedError()
        }
    }

    private val repository = StoreRepositoryImpl(mockApiService)

    @Test
    fun `getStoreInfo returns success when api call succeeds`() = runBlocking {
        // Given
        val expectedStoreDto = StoreDto(
            name = "Test Store",
            rating = 4.5,
            openingTime = "09:00",
            closingTime = "21:00"
        )
        mockApiService.storeToReturn = expectedStoreDto
        mockApiService.shouldThrowException = false

        // When
        val result = repository.getStoreInfo()

        // Then
        assertTrue(result.isSuccess)
        val store = result.getOrNull()!!
        assertEquals("Test Store", store.name)
        assertEquals(4.5, store.rating)
        assertEquals("09:00", store.openingTime)
        assertEquals("21:00", store.closingTime)
    }

    @Test
    fun `getStoreInfo returns failure when api call throws exception`() = runBlocking {
        // Given
        val testException = RuntimeException("Network error")
        mockApiService.shouldThrowException = true
        mockApiService.exceptionToThrow = testException

        // When
        val result = repository.getStoreInfo()

        // Then
        assertTrue(result.isFailure)
        val exception = result.exceptionOrNull()
        assertTrue(exception is AppException)
    }

    @Test
    fun `getStoreInfo maps dto to domain correctly`() = runBlocking {
        // Given
        val storeDto = StoreDto(
            name = "Amazing Store",
            rating = 3.8,
            openingTime = "08:30",
            closingTime = "22:15"
        )
        mockApiService.storeToReturn = storeDto
        mockApiService.shouldThrowException = false

        // When
        val result = repository.getStoreInfo()

        // Then
        assertTrue(result.isSuccess)
        val store = result.getOrNull()!!
        assertEquals(storeDto.name, store.name)
        assertEquals(storeDto.rating, store.rating)
        assertEquals(storeDto.openingTime, store.openingTime)
        assertEquals(storeDto.closingTime, store.closingTime)
    }
}