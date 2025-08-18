package com.nnzapp.easymarket.data.mapper

import com.nnzapp.easymarket.data.model.ProductDto
import com.nnzapp.easymarket.domain.model.Product

fun ProductDto.toDomain(): Product =
    Product(
        name = name,
        price = price,
        imageUrl = imageUrl,
    )

fun Product.toDto(): ProductDto =
    ProductDto(
        id = id,
        name = name,
        price = price,
        imageUrl = imageUrl,
    )
