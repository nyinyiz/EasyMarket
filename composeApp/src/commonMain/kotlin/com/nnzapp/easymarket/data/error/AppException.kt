package com.nnzapp.easymarket.data.error

import com.nnzapp.easymarket.domain.error.AppError
import com.nnzapp.easymarket.domain.error.asUserMessage

class AppException(
    val appError: AppError,
) : Exception(appError.asUserMessage())
