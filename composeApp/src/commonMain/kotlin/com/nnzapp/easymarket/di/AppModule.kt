package com.nnzapp.easymarket.di

import com.nnzapp.easymarket.data.remote.ApiService
import com.nnzapp.easymarket.data.remote.ApiServiceImpl
import com.nnzapp.easymarket.data.remote.createHttpClient
import com.nnzapp.easymarket.data.repository.OrderRepositoryImpl
import com.nnzapp.easymarket.data.repository.ProductRepositoryImpl
import com.nnzapp.easymarket.data.repository.StoreRepositoryImpl
import com.nnzapp.easymarket.domain.repository.OrderRepository
import com.nnzapp.easymarket.domain.repository.ProductRepository
import com.nnzapp.easymarket.domain.repository.StoreRepository
import org.koin.dsl.module

val appModule = module {
    // HTTP Client
    single { createHttpClient() }
    
    // API Service
    single<ApiService> { ApiServiceImpl(get()) }
    
    // Repositories
    single<StoreRepository> { StoreRepositoryImpl(get()) }
    single<ProductRepository> { ProductRepositoryImpl(get()) }
    single<OrderRepository> { OrderRepositoryImpl(get()) }
}