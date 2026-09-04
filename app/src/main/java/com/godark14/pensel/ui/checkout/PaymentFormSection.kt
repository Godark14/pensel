package com.godark14.pensel.ui.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.godark14.pensel.data.model.PaymentInfo
import com.godark14.pensel.ui.components.PenselTextField

@Composable
fun PaymentFormSection(
    paymentInfo: PaymentInfo,
    isActive: Boolean,
    errors: Map<String, String>,
    onUpdate: ((PaymentInfo) -> PaymentInfo) -> Unit,
    onBack: () -> Unit,
    onPlaceOrder: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Payment",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = if (isActive) MaterialTheme.colorScheme.onBackground
            else MaterialTheme.colorScheme.onSurfaceVariant
        )

        if (isActive) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PenselTextField(
                    label = "Cardholder Name",
                    value = paymentInfo.cardholderName,
                    onValueChange = { v -> onUpdate { it.copy(cardholderName = v) } },
                    required = true,
                    errorMessage = errors["cardholderName"]
                )

                PenselTextField(
                    label = "Card Number",
                    value = paymentInfo.cardNumber,
                    onValueChange = { v -> onUpdate { it.copy(cardNumber = v) } },
                    required = true,
                    placeholder = "1234 5678 9012 3456",
                    errorMessage = errors["cardNumber"]
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    PenselTextField(
                        label = "Expiry Date",
                        value = paymentInfo.expiryDate,
                        onValueChange = { v -> onUpdate { it.copy(expiryDate = v) } },
                        required = true,
                        placeholder = "MM/YY",
                        errorMessage = errors["expiryDate"],
                        modifier = Modifier.weight(1f)
                    )
                    PenselTextField(
                        label = "CVV",
                        value = paymentInfo.cvv,
                        onValueChange = { v -> onUpdate { it.copy(cvv = v) } },
                        required = true,
                        placeholder = "123",
                        errorMessage = errors["cvv"],
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    TextButton(onClick = onBack) {
                        Text("Back")
                    }
                    Button(
                        onClick = onPlaceOrder,
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Place Order", modifier = Modifier.padding(vertical = 6.dp))
                    }
                }
            }
        }
    }
}