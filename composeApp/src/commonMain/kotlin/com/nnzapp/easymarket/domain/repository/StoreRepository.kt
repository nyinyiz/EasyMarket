package com.nnzapp.easymarket.domain.repository

import com.nnzapp.easymarket.domain.model.Store

interface StoreRepository {
    suspend fun getStoreInfo(): Result<Store>
}
