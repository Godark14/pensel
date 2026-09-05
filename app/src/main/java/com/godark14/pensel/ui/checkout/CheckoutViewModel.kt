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
    val deliveryErrors: Map<String, String> = emptyMap(),
    val paymentErrors: Map<String, String> = emptyMap(),
    val shippingCost: Double = MockProductRepository.SHIPPING_COST,
    val couponCode: String = "",
    val isOrderSummaryExpanded: Boolean = true,
    val isOrderPlaced: Boolean = false
)

class CheckoutViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CheckoutUiState())
    val uiState: StateFlow<CheckoutUiState> = _uiState.asStateFlow()

    fun updateDeliveryInfo(update: (DeliveryInfo) -> DeliveryInfo) {
        _uiState.update {
            val newInfo = update(it.deliveryInfo)
            it.copy(
                deliveryInfo = newInfo,
                deliveryErrors = if (it.deliveryErrors.isNotEmpty()) {
                    CheckoutValidator.validateDelivery(newInfo)
                } else {
                    it.deliveryErrors
                }
            )
        }
    }

    fun updatePaymentInfo(update: (PaymentInfo) -> PaymentInfo) {
        _uiState.update {
            val newInfo = update(it.paymentInfo)
            it.copy(
                paymentInfo = newInfo,
                paymentErrors = if (it.paymentErrors.isNotEmpty()) {
                    CheckoutValidator.validatePayment(newInfo)
                } else {
                    it.paymentErrors
                }
            )
        }
    }

    fun goToPaymentStep() {
        val errors = CheckoutValidator.validateDelivery(_uiState.value.deliveryInfo)
        if (errors.isEmpty()) {
            _uiState.update { it.copy(currentStep = CheckoutStep.PAYMENT, deliveryErrors = emptyMap()) }
        } else {
            _uiState.update { it.copy(deliveryErrors = errors) }
        }
    }

    fun goToDeliveryStep() {
        _uiState.update { it.copy(currentStep = CheckoutStep.DELIVERY) }
    }

    fun placeOrder() {
        val errors = CheckoutValidator.validatePayment(_uiState.value.paymentInfo)
        if (errors.isEmpty()) {
            _uiState.update { it.copy(paymentErrors = emptyMap(), isOrderPlaced = true) }
        } else {
            _uiState.update { it.copy(paymentErrors = errors) }
        }
    }

    fun dismissOrderConfirmation() {
        _uiState.update { it.copy(isOrderPlaced = false) }
    }

    fun updateCouponCode(code: String) {
        _uiState.update { it.copy(couponCode = code) }
    }

    fun toggleOrderSummary() {
        _uiState.update { it.copy(isOrderSummaryExpanded = !it.isOrderSummaryExpanded) }
    }
}