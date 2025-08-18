package com.nnzapp.easymarket.presentation.navigation

sealed class Screen(
    val route: String,
) {
    object Config : Screen("config")

    object Store : Screen("store")

    object OrderSummary : Screen("order_summary")

    object Success : Screen("success")
}
