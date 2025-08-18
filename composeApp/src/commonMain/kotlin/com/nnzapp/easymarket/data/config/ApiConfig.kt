package com.nnzapp.easymarket.data.config

data class ApiRoute(
    val name: String,
    val url: String,
    val description: String,
)

object ApiConfig {
    private var _useMockApi: Boolean = false
    private var _baseUrl: String = "https://mobile-coding-challenge-api-5.free.beeceptor.com"

    val predefinedRoutes =
        listOf(
            ApiRoute(
                name = "Original API endpoint",
                url = "https://mobile-coding-challenge-api.free.beeceptor.com",
                description = "Original API endpoint",
            ),
            ApiRoute(
                name = "Alternative API endpoint",
                url = "https://mobile-coding-challenge-api-8.free.beeceptor.com",
                description = "Alternative API endpoint",
            ),
        )

    var useMockApi: Boolean
        get() = _useMockApi
        set(value) {
            _useMockApi = value
        }

    var baseUrl: String
        get() = _baseUrl
        set(value) {
            _baseUrl = value.trimEnd('/')
        }
}
