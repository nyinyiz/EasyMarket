package com.nnzapp.easymarket.data.remote

import com.nnzapp.easymarket.data.config.ApiConfig
import com.nnzapp.easymarket.data.error.AppException
import com.nnzapp.easymarket.data.error.toAppException
import com.nnzapp.easymarket.data.model.OrderRequestDto
import com.nnzapp.easymarket.data.model.ProductDto
import com.nnzapp.easymarket.data.model.ProductResponseDto
import com.nnzapp.easymarket.data.model.StoreDto
import com.nnzapp.easymarket.domain.error.AppError
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType

class ApiServiceImpl(
    private val httpClient: HttpClient,
) : ApiService {
    private val baseUrl: String
        get() = ApiConfig.baseUrl.ifEmpty { "https://mobile-coding-challenge-api-5.free.beeceptor.com" }

    override suspend fun getStoreInfo(): StoreDto =
        try {
            httpClient.get("$baseUrl/storeInfo").body()
        } catch (t: Throwable) {
            throw t.toAppException()
        }

    override suspend fun getProducts(): List<ProductDto> =
        try {
            val response: ProductResponseDto = httpClient.get("$baseUrl/products").body()
            response.data.ProductResult.Products
        } catch (t: Throwable) {
            throw t.toAppException()
        }

    override suspend fun placeOrder(orderRequest: OrderRequestDto): Boolean =
        try {
            val response =
                httpClient.post("$baseUrl/order") {
                    contentType(ContentType.Application.Json)
                    setBody(orderRequest)
                }
            val code = response.status
            if (code.value in 200..299) {
                true
            } else if (code == HttpStatusCode.TooManyRequests) {
                throw AppException(AppError.TooManyRequests())
            } else {
                throw AppException(AppError.Http(code.value, response.bodyAsText().takeIf { it.isNotBlank() }))
            }
        } catch (t: Throwable) {
            throw t.toAppException()
        }
}
