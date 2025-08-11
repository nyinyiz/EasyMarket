package com.nnzapp.easymarket.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nnzapp.easymarket.presentation.screen.OrderSummaryScreen
import com.nnzapp.easymarket.presentation.screen.StoreScreen
import com.nnzapp.easymarket.presentation.screen.SuccessScreen
import com.nnzapp.easymarket.presentation.viewmodel.StoreViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EasyMarketNavigation(navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = Screen.Store.route,
    ) {
        composable(Screen.Store.route) {
            val storeOwner = remember(navController) { navController.getBackStackEntry(Screen.Store.route) }
            val storeViewModel: StoreViewModel = koinViewModel(viewModelStoreOwner = storeOwner)
            StoreScreen(
                onNavigateToOrderSummary = {
                    navController.navigate(Screen.OrderSummary.route)
                },
                viewModel = storeViewModel,
            )
        }

        composable(Screen.OrderSummary.route) {
            val storeOwner = remember(navController) { navController.getBackStackEntry(Screen.Store.route) }
            val storeViewModel: StoreViewModel = koinViewModel(viewModelStoreOwner = storeOwner)
            OrderSummaryScreen(
                onNavigateToSuccess = {
                    navController.navigate(Screen.Success.route) {
                        popUpTo(Screen.Store.route)
                    }
                },
                onNavigateBack = {
                    navController.popBackStack()
                },
                viewModel = storeViewModel,
            )
        }

        composable(Screen.Success.route) {
            val storeOwner = remember(navController) { navController.getBackStackEntry(Screen.Store.route) }
            val storeViewModel: StoreViewModel = koinViewModel(viewModelStoreOwner = storeOwner)
            SuccessScreen(
                onNavigateToStore = {
                    navController.popBackStack(Screen.Store.route, inclusive = false)
                },
                viewModel = storeViewModel,
            )
        }
    }
}
