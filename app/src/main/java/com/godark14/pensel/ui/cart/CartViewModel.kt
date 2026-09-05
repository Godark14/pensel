package com.godark14.pensel.ui.cart

import androidx.lifecycle.ViewModel
import com.godark14.pensel.data.model.CartItem
import com.godark14.pensel.data.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    fun addToCart(product: Product, quantity: Int = 1) {
        _cartItems.update { items ->
            val existing = items.find { it.product.id == product.id }
            if (existing != null) {
                items.map {
                    if (it.product.id == product.id) it.copy(quantity = it.quantity + quantity) else it
                }
            } else {
                items + CartItem(product = product, quantity = quantity)
            }
        }
    }

    fun updateQuantity(productId: String, newQuantity: Int) {
        if (newQuantity < 1) return
        _cartItems.update { items ->
            items.map { if (it.product.id == productId) it.copy(quantity = newQuantity) else it }
        }
    }

    fun removeFromCart(productId: String) {
        _cartItems.update { items -> items.filterNot { it.product.id == productId } }
    }

    fun clearCart() {
        _cartItems.update { emptyList() }
    }

    fun getQuantityInCart(productId: String): Int =
        _cartItems.value.find { it.product.id == productId }?.quantity ?: 0
}