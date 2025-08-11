package com.nnzapp.easymarket.di

import com.nnzapp.easymarket.data.remote.ApiService
import com.nnzapp.easymarket.data.remote.ApiServiceImpl
import com.nnzapp.easymarket.data.remote.MockApiServiceImpl
import com.nnzapp.easymarket.data.remote.createHttpClient
import com.nnzapp.easymarket.data.repository.OrderRepositoryImpl
import com.nnzapp.easymarket.data.repository.ProductRepositoryImpl
import com.nnzapp.easymarket.data.repository.StoreRepositoryImpl
import com.nnzapp.easymarket.domain.repository.OrderRepository
import com.nnzapp.easymarket.domain.repository.ProductRepository
import com.nnzapp.easymarket.domain.repository.StoreRepository
import com.nnzapp.easymarket.domain.usecase.GetProductsUseCase
import com.nnzapp.easymarket.domain.usecase.GetStoreInfoUseCase
import com.nnzapp.easymarket.domain.usecase.PlaceOrderUseCase
import com.nnzapp.easymarket.presentation.viewmodel.StoreViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val USE_MOCK_API = true

val appModule =
    module {
        // Networking
        single { createHttpClient() }
        single<ApiService> {
            if (USE_MOCK_API) MockApiServiceImpl() else ApiServiceImpl(get())
        }

        // Repositories
        single<StoreRepository> { StoreRepositoryImpl(get()) }
        single<ProductRepository> { ProductRepositoryImpl(get()) }
        single<OrderRepository> { OrderRepositoryImpl(get()) }

        // Use Cases
        single { GetStoreInfoUseCase(get()) }
        single { GetProductsUseCase(get()) }
        single { PlaceOrderUseCase(get()) }

        // ViewModels
        viewModel { StoreViewModel(get(), get(), get()) }
    }
