package com.nnzapp.easymarket

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform