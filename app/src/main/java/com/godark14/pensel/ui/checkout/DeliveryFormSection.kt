package com.godark14.pensel.ui.checkout

import androidx.compose.animation.AnimatedVisibility
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
import com.godark14.pensel.data.model.DeliveryInfo
import com.godark14.pensel.ui.components.PenselTextField

@Composable
fun DeliveryFormSection(
    deliveryInfo: DeliveryInfo,
    isActive: Boolean,
    errors: Map<String, String>,
    onUpdate: ((DeliveryInfo) -> DeliveryInfo) -> Unit,
    onContinue: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Delivery Options",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            if (!isActive) {
                TextButton(onClick = onEdit) {
                    Text("Edit")
                }
            }
        }

        AnimatedVisibility(visible = isActive) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    PenselTextField(
                        label = "First Name",
                        value = deliveryInfo.firstName,
                        onValueChange = { v -> onUpdate { it.copy(firstName = v) } },
                        required = true,
                        errorMessage = errors["firstName"],
                        modifier = Modifier.weight(1f)
                    )
                    PenselTextField(
                        label = "Last Name",
                        value = deliveryInfo.lastName,
                        onValueChange = { v -> onUpdate { it.copy(lastName = v) } },
                        required = true,
                        errorMessage = errors["lastName"],
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    PenselTextField(
                        label = "Email",
                        value = deliveryInfo.email,
                        onValueChange = { v -> onUpdate { it.copy(email = v) } },
                        required = true,
                        errorMessage = errors["email"],
                        modifier = Modifier.weight(1f)
                    )
                    PenselTextField(
                        label = "Phone Number",
                        value = deliveryInfo.phoneNumber,
                        onValueChange = { v -> onUpdate { it.copy(phoneNumber = v) } },
                        required = true,
                        errorMessage = errors["phoneNumber"],
                        modifier = Modifier.weight(1f)
                    )
                }

                PenselTextField(
                    label = "Country / Region",
                    value = deliveryInfo.countryRegion,
                    onValueChange = { v -> onUpdate { it.copy(countryRegion = v) } },
                    required = true,
                    errorMessage = errors["countryRegion"]
                )

                PenselTextField(
                    label = "Address",
                    value = deliveryInfo.address,
                    onValueChange = { v -> onUpdate { it.copy(address = v) } },
                    required = true,
                    errorMessage = errors["address"]
                )

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    PenselTextField(
                        label = "City",
                        value = deliveryInfo.city,
                        onValueChange = { v -> onUpdate { it.copy(city = v) } },
                        required = true,
                        errorMessage = errors["city"],
                        modifier = Modifier.weight(1f)
                    )
                    PenselTextField(
                        label = "State",
                        value = deliveryInfo.state,
                        onValueChange = { v -> onUpdate { it.copy(state = v) } },
                        required = true,
                        errorMessage = errors["state"],
                        modifier = Modifier.weight(1f)
                    )
                    PenselTextField(
                        label = "Postal Code",
                        value = deliveryInfo.postalCode,
                        onValueChange = { v -> onUpdate { it.copy(postalCode = v) } },
                        required = true,
                        errorMessage = errors["postalCode"],
                        modifier = Modifier.weight(1f)
                    )
                }

                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Save & Continue", modifier = Modifier.padding(vertical = 6.dp))
                }
            }
        }
    }
}