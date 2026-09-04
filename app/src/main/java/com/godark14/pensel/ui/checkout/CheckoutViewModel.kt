package com.godark14.pensel.ui.checkout

import androidx.lifecycle.ViewModel
import com.godark14.pensel.data.mock.MockProductRepository
import com.godark14.pensel.data.model.CartItem
import com.godark14.pensel.data.model.DeliveryInfo
import com.godark14.pensel.data.model.PaymentInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class CheckoutUiState(
    val currentStep: CheckoutStep = CheckoutStep.DELIVERY,
    val deliveryInfo: DeliveryInfo = DeliveryInfo(),
    val paymentInfo: PaymentInfo = PaymentInfo(),
    val cartItems: List<CartItem> = emptyList(),
    val shippingCost: Double = 0.0,
    val couponCode: String = "",
    val isOrderSummaryExpanded: Boolean = true
) {
    val subtotal: Double
        get() = cartItems.sumOf { it.product.price * it.quantity }

    val total: Double
        get() = subtotal + shippingCost
}

class CheckoutViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        CheckoutUiState(
            cartItems = MockProductRepository.getMockCart(),
            shippingCost = MockProductRepository.SHIPPING_COST
        )
    )
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    fun updateDeliveryInfo(update: (DeliveryInfo) -> DeliveryInfo) {
        _uiState.update { it.copy(deliveryInfo = update(it.deliveryInfo)) }
    }

    fun updatePaymentInfo(update: (PaymentInfo) -> PaymentInfo) {
        _uiState.update { it.copy(paymentInfo = update(it.paymentInfo)) }
    }

    fun goToPaymentStep() {
        _uiState.update { it.copy(currentStep = CheckoutStep.PAYMENT) }
    }

    fun goToDeliveryStep() {
        _uiState.update { it.copy(currentStep = CheckoutStep.DELIVERY) }
    }

    fun updateQuantity(itemId: String, newQuantity: Int) {
        if (newQuantity < 1) return
        _uiState.update { state ->
            state.copy(
                cartItems = state.cartItems.map {
                    if (it.product.id == itemId) it.copy(quantity = newQuantity) else it
                }
            )
        }
    }

    fun updateCouponCode(code: String) {
        _uiState.update { it.copy(couponCode = code) }
    }

    fun toggleOrderSummary() {
        _uiState.update { it.copy(isOrderSummaryExpanded = !it.isOrderSummaryExpanded) }
    }
}