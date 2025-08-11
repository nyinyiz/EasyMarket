package com.nnzapp.easymarket

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.nnzapp.easymarket.di.appModule
import com.nnzapp.easymarket.presentation.navigation.EasyMarketNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        MaterialTheme {
            EasyMarketNavigation()
        }
    }
}
