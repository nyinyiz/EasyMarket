package com.nnzapp.easymarket.data.remote

import com.nnzapp.easymarket.data.model.OrderRequestDto
import com.nnzapp.easymarket.data.model.ProductDto
import com.nnzapp.easymarket.data.model.StoreDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ApiServiceImpl(
    private val httpClient: HttpClient,
) : ApiService {
    companion object {
        private const val BASE_URL = "https://c8d92d0a-6233-4ef7-a229-5a91deb91ea1.mock.pstmn.io"
    }

    override suspend fun getStoreInfo(): StoreDto = httpClient.get("$BASE_URL/storeInfo").body()

    override suspend fun getProducts(): List<ProductDto> = httpClient.get("$BASE_URL/products").body()

    override suspend fun placeOrder(orderRequest: OrderRequestDto): Boolean {
        val response =
            httpClient.post("$BASE_URL/order") {
                contentType(ContentType.Application.Json)
                setBody(orderRequest)
            }
        return response.status.value in 200..299
    }
}
