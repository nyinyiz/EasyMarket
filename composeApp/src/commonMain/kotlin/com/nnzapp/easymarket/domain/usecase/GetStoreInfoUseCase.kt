package com.nnzapp.easymarket.domain.usecase

import com.nnzapp.easymarket.domain.repository.StoreRepository

class GetStoreInfoUseCase(
    private val storeRepository: StoreRepository,
) {
    suspend operator fun invoke() = storeRepository.getStoreInfo()
}
