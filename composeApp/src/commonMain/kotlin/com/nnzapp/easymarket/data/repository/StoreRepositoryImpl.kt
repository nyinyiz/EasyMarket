package com.nnzapp.easymarket.data.repository

import com.nnzapp.easymarket.data.error.toAppException
import com.nnzapp.easymarket.data.mapper.toDomain
import com.nnzapp.easymarket.data.remote.ApiService
import com.nnzapp.easymarket.domain.model.Store
import com.nnzapp.easymarket.domain.repository.StoreRepository

class StoreRepositoryImpl(
    private val apiService: ApiService,
) : StoreRepository {
    override suspend fun getStoreInfo(): Result<Store> =
        try {
            val storeDto = apiService.getStoreInfo()
            Result.success(storeDto.toDomain())
        } catch (t: Throwable) {
            Result.failure(t.toAppException())
        }
}
