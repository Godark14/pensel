package com.godark14.pensel.ui.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.godark14.pensel.data.model.CartItem
import com.godark14.pensel.fold.FoldPosture
import com.godark14.pensel.fold.rememberFoldPosture
import com.godark14.pensel.ui.cart.CartViewModel

@Composable
fun CheckoutScreen(
    cartViewModel: CartViewModel,
    viewModel: CheckoutViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val foldPosture = rememberFoldPosture()

    val subtotal = cartItems.sumOf { it.product.price * it.quantity }
    val total = subtotal + uiState.shippingCost

    if (uiState.isOrderPlaced) {
        OrderConfirmationDialog(
            total = total,
            onDismiss = viewModel::dismissOrderConfirmation
        )
    }

    when (foldPosture) {
        FoldPosture.CLOSED -> CheckoutScreenClosed(
            uiState = uiState,
            cartItems = cartItems,
            subtotal = subtotal,
            total = total,
            viewModel = viewModel,
            cartViewModel = cartViewModel,
            modifier = modifier
        )
        FoldPosture.OPENED -> CheckoutScreenOpened(
            uiState = uiState,
            cartItems = cartItems,
            subtotal = subtotal,
            total = total,
            viewModel = viewModel,
            cartViewModel = cartViewModel,
            modifier = modifier
        )
    }
}

@Composable
private fun CheckoutScreenClosed(
    uiState: CheckoutUiState,
    cartItems: List<CartItem>,
    subtotal: Double,
    total: Double,
    viewModel: CheckoutViewModel,
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        DeliveryFormSection(
            deliveryInfo = uiState.deliveryInfo,
            isActive = uiState.currentStep == CheckoutStep.DELIVERY,
            errors = uiState.deliveryErrors,
            onUpdate = viewModel::updateDeliveryInfo,
            onContinue = viewModel::goToPaymentStep,
            onEdit = viewModel::goToDeliveryStep
        )

        PaymentFormSection(
            paymentInfo = uiState.paymentInfo,
            isActive = uiState.currentStep == CheckoutStep.PAYMENT,
            errors = uiState.paymentErrors,
            onUpdate = viewModel::updatePaymentInfo,
            onBack = viewModel::goToDeliveryStep,
            onPlaceOrder = viewModel::placeOrder
        )

        OrderSummarySection(
            cartItems = cartItems,
            subtotal = subtotal,
            shippingCost = uiState.shippingCost,
            total = total,
            couponCode = uiState.couponCode,
            isExpanded = uiState.isOrderSummaryExpanded,
            onToggleExpand = viewModel::toggleOrderSummary,
            onQuantityChange = cartViewModel::updateQuantity,
            onCouponCodeChange = viewModel::updateCouponCode,
            showToggle = true
        )
    }
}

@Composable
private fun CheckoutScreenOpened(
    uiState: CheckoutUiState,
    cartItems: List<CartItem>,
    subtotal: Double,
    total: Double,
    viewModel: CheckoutViewModel,
    cartViewModel: CartViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            DeliveryFormSection(
                deliveryInfo = uiState.deliveryInfo,
                isActive = uiState.currentStep == CheckoutStep.DELIVERY,
                errors = uiState.deliveryErrors,
                onUpdate = viewModel::updateDeliveryInfo,
                onContinue = viewModel::goToPaymentStep,
                onEdit = viewModel::goToDeliveryStep
            )

            PaymentFormSection(
                paymentInfo = uiState.paymentInfo,
                isActive = uiState.currentStep == CheckoutStep.PAYMENT,
                errors = uiState.paymentErrors,
                onUpdate = viewModel::updatePaymentInfo,
                onBack = viewModel::goToDeliveryStep,
                onPlaceOrder = viewModel::placeOrder,
                modifier = Modifier.padding(top = 24.dp)
            )
        }

        Column(
            modifier = Modifier
                .width(380.dp)
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .padding(20.dp)
        ) {
            OrderSummarySection(
                cartItems = cartItems,
                subtotal = subtotal,
                shippingCost = uiState.shippingCost,
                total = total,
                couponCode = uiState.couponCode,
                isExpanded = true,
                onToggleExpand = {},
                onQuantityChange = cartViewModel::updateQuantity,
                onCouponCodeChange = viewModel::updateCouponCode,
                showToggle = false
            )
        }
    }
}