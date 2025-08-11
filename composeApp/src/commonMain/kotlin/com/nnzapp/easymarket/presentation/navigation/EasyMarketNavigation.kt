package com.nnzapp.easymarket.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nnzapp.easymarket.presentation.screen.OrderSummaryScreen
import com.nnzapp.easymarket.presentation.screen.StoreScreen
import com.nnzapp.easymarket.presentation.screen.SuccessScreen

@Composable
fun EasyMarketNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Store.route,
    ) {
        composable(Screen.Store.route) {
            StoreScreen(
                onNavigateToOrderSummary = {
                    navController.navigate(Screen.OrderSummary.route)
                },
            )
        }

        composable(Screen.OrderSummary.route) {
            OrderSummaryScreen(
                onNavigateToSuccess = {
                    navController.navigate(Screen.Success.route) {
                        popUpTo(Screen.Store.route)
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
            )
        }

        composable(Screen.Success.route) {
            SuccessScreen(
                onNavigateToStore = {
                    navController.popBackStack(Screen.Store.route, inclusive = false)
                },
            )
        }
    }
}
