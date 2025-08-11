package com.nnzapp.easymarket

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nnzapp.easymarket.data.model.OrderRequestDto
import com.nnzapp.easymarket.data.model.ProductDto
import com.nnzapp.easymarket.data.remote.ApiService
import com.nnzapp.easymarket.di.appModule
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        MaterialTheme {
            NetworkDebugScreen()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NetworkDebugScreen() {
    val apiService: ApiService = koinInject()
    val scope = rememberCoroutineScope()
    
    var responseText by remember { mutableStateOf("No requests made yet") }
    var isLoading by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Network Debug Console",
            style = MaterialTheme.typography.headlineMedium
        )
        
        // Store Info Button
        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    responseText = try {
                        val result = apiService.getStoreInfo()
                        "GET /storeInfo\n\nResponse:\n${result}"
                    } catch (e: Exception) {
                        "GET /storeInfo\n\nError:\n${e.message}"
                    }
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Get Store Info")
        }
        
        // Products Button
        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    responseText = try {
                        val result = apiService.getProducts()
                        "GET /products\n\nResponse:\n${result.joinToString("\n") { "- ${it.name}: $${it.price}" }}"
                    } catch (e: Exception) {
                        "GET /products\n\nError:\n${e.message}"
                    }
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Get Products")
        }
        
        // Place Order Button
        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    responseText = try {
                        val sampleOrder = OrderRequestDto(
                            products = listOf(
                                ProductDto("Sample Product", 1000, "https://example.com/image.jpg")
                            ),
                            deliveryAddress = "123 Test Street"
                        )
                        val result = apiService.placeOrder(sampleOrder)
                        "POST /order\n\nRequest:\n${sampleOrder}\n\nResponse:\nSuccess: $result"
                    } catch (e: Exception) {
                        "POST /order\n\nError:\n${e.message}"
                    }
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        ) {
            Text("Place Sample Order")
        }
        
        HorizontalDivider()
        
        // Response Display
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Response:",
                style = MaterialTheme.typography.titleMedium
            )
            if (isLoading) {
                Spacer(modifier = Modifier.width(8.dp))
                CircularProgressIndicator(modifier = Modifier.size(16.dp))
            }
        }
        
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = responseText,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}