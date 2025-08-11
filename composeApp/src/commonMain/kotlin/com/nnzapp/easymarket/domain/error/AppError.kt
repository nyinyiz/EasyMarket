package com.nnzapp.easymarket.domain.error

sealed class AppError {
    object Network : AppError()

    data class TooManyRequests(
        val retryAfterSeconds: Long? = null,
    ) : AppError()

    data class Http(
        val statusCode: Int,
        val message: String? = null,
    ) : AppError()

    data class Serialization(
        val message: String? = null,
    ) : AppError()

    data class Unknown(
        val message: String? = null,
    ) : AppError()
}

fun AppError.asUserMessage(): String =
    when (this) {
        is AppError.Network -> "Network error. Please check your internet connection and try again."
        is AppError.TooManyRequests -> "You’ve hit the request limit. Please try again in a moment."
        is AppError.Http ->
            when (statusCode) {
                401 -> "You are not authorized. Please sign in and try again."
                403 -> "Access denied."
                404 -> "Requested content was not found."
                500 -> "Server error. Please try again later."
                else -> message ?: "Unexpected server response ($statusCode)."
            }
        is AppError.Serialization -> message ?: "We couldn’t process the data from server."
        is AppError.Unknown -> message ?: "Something went wrong. Please try again."
    }
