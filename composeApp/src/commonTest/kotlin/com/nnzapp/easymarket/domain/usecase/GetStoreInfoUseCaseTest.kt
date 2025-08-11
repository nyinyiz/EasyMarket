package com.nnzapp.easymarket.domain.usecase

import com.nnzapp.easymarket.domain.model.Store
import com.nnzapp.easymarket.domain.repository.StoreRepository
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetStoreInfoUseCaseTest {
    private val mockStoreRepository =
        object : StoreRepository {
            var resultToReturn: Result<Store> =
                Result.success(
                    Store(name = "Default", rating = 0.0, openingTime = "00:00", closingTime = "00:00"),
                )

            override suspend fun getStoreInfo(): Result<Store> = resultToReturn
        }

    private val useCase = GetStoreInfoUseCase(mockStoreRepository)

    @Test
    fun `invoke returns success when repository returns success`() =
        runBlocking {
            // Given
            val expectedStore =
                Store(
                    name = "Test Store",
                    rating = 4.5,
                    openingTime = "09:00",
                    closingTime = "21:00",
                )
            mockStoreRepository.resultToReturn = Result.success(expectedStore)

            // When
            val result = useCase.invoke()

            // Then
            assertTrue(result.isSuccess)
            val store = result.getOrNull()!!
            assertEquals("Test Store", store.name)
            assertEquals(4.5, store.rating)
            assertEquals("09:00", store.openingTime)
            assertEquals("21:00", store.closingTime)
        }

    @Test
    fun `invoke returns failure when repository returns failure`() =
        runBlocking {
            // Given
            val expectedError = RuntimeException("Network error")
            mockStoreRepository.resultToReturn = Result.failure(expectedError)

            // When
            val result = useCase.invoke()

            // Then
            assertTrue(result.isFailure)
            assertEquals(expectedError, result.exceptionOrNull())
        }

    @Test
    fun `invoke delegates to store repository`() =
        runBlocking {
            // Given
            val testStore =
                Store(
                    name = "Amazing Store",
                    rating = 3.8,
                    openingTime = "08:30",
                    closingTime = "22:15",
                )
            mockStoreRepository.resultToReturn = Result.success(testStore)

            // When
            val result = useCase()

            // Then
            assertTrue(result.isSuccess)
            assertEquals(testStore, result.getOrNull())
        }

    @Test
    fun `invoke handles store with edge case values`() =
        runBlocking {
            // Given
            val edgeCaseStore =
                Store(
                    name = "",
                    rating = 0.0,
                    openingTime = "24:00",
                    closingTime = "00:00",
                )
            mockStoreRepository.resultToReturn = Result.success(edgeCaseStore)

            // When
            val result = useCase.invoke()

            // Then
            assertTrue(result.isSuccess)
            val store = result.getOrNull()!!
            assertEquals("", store.name)
            assertEquals(0.0, store.rating)
            assertEquals("24:00", store.openingTime)
            assertEquals("00:00", store.closingTime)
        }
}
