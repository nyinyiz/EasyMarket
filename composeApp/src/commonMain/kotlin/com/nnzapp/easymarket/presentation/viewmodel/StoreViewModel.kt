package com.nnzapp.easymarket.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nnzapp.easymarket.domain.model.CartItem
import com.nnzapp.easymarket.domain.model.Product
import com.nnzapp.easymarket.domain.model.Store
import com.nnzapp.easymarket.domain.usecase.GetProductsUseCase
import com.nnzapp.easymarket.domain.usecase.GetStoreInfoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StoreUiState(
    val isLoading: Boolean = false,
    val store: Store? = null,
    val products: List<Product> = emptyList(),
    val cartItems: List<CartItem> = emptyList(),
    val errorMessage: String? = null,
) {
    val totalCartItems: Double get() = cartItems.sumOf { it.quantity }
    val totalPrice: Double get() = cartItems.sumOf { it.totalPrice }
}

class StoreViewModel(
    private val getStoreInfoUseCase: GetStoreInfoUseCase,
    private val getProductsUseCase: GetProductsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(StoreUiState())
    val uiState: StateFlow<StoreUiState> = _uiState.asStateFlow()

    init {
        loadStoreData()
    }

    private fun loadStoreData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                val storeResult = getStoreInfoUseCase()
                val productsResult = getProductsUseCase()

                var newState = _uiState.value.copy(isLoading = false)

                storeResult.fold(
                    onSuccess = { store ->
                        newState = newState.copy(store = store)
                    },
                    onFailure = { error ->
                        newState =
                            newState.copy(errorMessage = "Failed to load store info: ${error.message}")
                    },
                )

                productsResult.fold(
                    onSuccess = { products ->
                        newState = newState.copy(products = products)
                    },
                    onFailure = { error ->
                        newState =
                            newState.copy(errorMessage = "Failed to load products: ${error.message}")
                    },
                )

                _uiState.value = newState
            } catch (e: Exception) {
                _uiState.value =
                    _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "An unexpected error occurred: ${e.message}",
                    )
            }
        }
    }

    fun addToCart(product: Product) {
        val currentItems = _uiState.value.cartItems.toMutableList()
        val existingItemIndex = currentItems.indexOfFirst { it.product.name == product.name }

        if (existingItemIndex >= 0) {
            currentItems[existingItemIndex] =
                currentItems[existingItemIndex].copy(
                    quantity = currentItems[existingItemIndex].quantity + 1,
                )
        } else {
            currentItems.add(CartItem(product, 1.0))
        }

        _uiState.value = _uiState.value.copy(cartItems = currentItems)
    }

    fun removeFromCart(product: Product) {
        val currentItems = _uiState.value.cartItems.toMutableList()
        val existingItemIndex = currentItems.indexOfFirst { it.product.name == product.name }

        if (existingItemIndex >= 0) {
            val currentItem = currentItems[existingItemIndex]
            if (currentItem.quantity > 1) {
                currentItems[existingItemIndex] =
                    currentItem.copy(quantity = currentItem.quantity - 1)
            } else {
                currentItems.removeAt(existingItemIndex)
            }
        }

        _uiState.value = _uiState.value.copy(cartItems = currentItems)
    }

    fun getProductQuantity(product: Product): Double =
        _uiState.value.cartItems
            .find { it.product.name == product.name }
            ?.quantity ?: 0.0

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    fun refreshData() {
        loadStoreData()
    }
}
