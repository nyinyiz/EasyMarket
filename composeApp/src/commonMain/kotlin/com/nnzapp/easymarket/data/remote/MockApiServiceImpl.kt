package com.nnzapp.easymarket.data.remote

import com.nnzapp.easymarket.data.model.OrderRequestDto
import com.nnzapp.easymarket.data.model.ProductDto
import com.nnzapp.easymarket.data.model.StoreDto
import kotlinx.coroutines.delay

/**
 * {
"error": {
"name": "usageLimitError",
"header": "Usage limit reached",
"message": "Your team plan allows 1000 mock server calls per month. Contact your team Admin to up your limit."
}
}

There has api response 429 Too Many Requests error and server limitation reached. So I have created this mock api service.

by @Nyi (11 Aug 2025)

 */
class MockApiServiceImpl : ApiService {
    override suspend fun getStoreInfo(): StoreDto {
        delay(500)

        return StoreDto(
            name = "Fresh Market",
            rating = 4.8,
            openingTime = "8:00 AM",
            closingTime = "10:00 PM",
        )
    }

    override suspend fun getProducts(): List<ProductDto> {
        delay(800)

        return listOf(
            ProductDto(
                name = "Fresh Apples",
                price = 5,
                imageUrl = "https://picsum.photos/seed/picsum/200/300",
            ),
            ProductDto(
                name = "Organic Bananas",
                price = 3,
                imageUrl = "https://picsum.photos/seed/picsum/200/300",
            ),
            ProductDto(
                name = "Fresh Oranges",
                price = 4,
                imageUrl = "https://picsum.photos/seed/picsum/200/300",
            ),
            ProductDto(
                name = "Tomatoes",
                price = 6,
                imageUrl = "https://picsum.photos/seed/picsum/200/300",
            ),
            ProductDto(
                name = "Broccoli",
                price = 4,
                imageUrl = "https://picsum.photos/seed/picsum/200/300",
            ),
            ProductDto(
                name = "Carrots",
                price = 3,
                imageUrl = "https://picsum.photos/seed/picsum/200/300",
            ),
            ProductDto(
                name = "Fresh Bread",
                price = 4,
                imageUrl = "https://picsum.photos/seed/picsum/200/300",
            ),
            ProductDto(
                name = "Organic Milk",
                price = 5,
                imageUrl = "https://picsum.photos/seed/picsum/200/300",
            ),
            ProductDto(
                name = "Free Range Eggs",
                price = 6,
                imageUrl = "https://picsum.photos/seed/picsum/200/300",
            ),
            ProductDto(
                name = "Greek Yogurt",
                price = 4,
                imageUrl = "https://picsum.photos/seed/picsum/200/300",
            ),
            ProductDto(
                name = "Strawberries",
                price = 7,
                imageUrl = "https://picsum.photos/seed/picsum/200/300",
            ),
            ProductDto(
                name = "Avocados",
                price = 8,
                imageUrl = "https://picsum.photos/seed/picsum/200/300",
            ),
        )
    }

    override suspend fun placeOrder(orderRequest: OrderRequestDto): Boolean {
        delay(1000)

        return true
    }
}
