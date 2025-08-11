package com.nnzapp.easymarket.data.error

import com.nnzapp.easymarket.domain.error.AppError
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.http.HttpStatusCode
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.serialization.SerializationException

fun Throwable.toAppException(): AppException =
    when (this) {
        is AppException -> this
        is ClientRequestException -> {
            val status = response.status.value
            if (status == HttpStatusCode.TooManyRequests.value) {
                AppException(AppError.TooManyRequests())
            } else {
                AppException(AppError.Http(status, message))
            }
        }
        is ServerResponseException -> AppException(AppError.Http(response.status.value, message))
        is IOException, is TimeoutCancellationException -> AppException(AppError.Network)
        is SerializationException -> AppException(AppError.Serialization(message))
        else -> AppException(AppError.Unknown(message))
    }
