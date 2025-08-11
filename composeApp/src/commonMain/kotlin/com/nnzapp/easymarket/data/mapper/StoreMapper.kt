package com.nnzapp.easymarket.data.mapper

import com.nnzapp.easymarket.data.model.StoreDto
import com.nnzapp.easymarket.domain.model.Store

fun StoreDto.toDomain(): Store =
    Store(
        name = name,
        rating = rating,
        openingTime = openingTime,
        closingTime = closingTime,
    )
