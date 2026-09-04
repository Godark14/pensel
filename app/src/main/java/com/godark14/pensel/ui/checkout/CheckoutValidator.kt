package com.godark14.pensel.ui.checkout

import com.godark14.pensel.data.model.DeliveryInfo
import com.godark14.pensel.data.model.PaymentInfo

object CheckoutValidator {

    fun validateDelivery(info: DeliveryInfo): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        if (info.firstName.isBlank()) errors["firstName"] = "First name is required"
        if (info.lastName.isBlank()) errors["lastName"] = "Last name is required"

        if (info.email.isBlank()) {
            errors["email"] = "Email is required"
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(info.email).matches()) {
            errors["email"] = "Enter a valid email"
        }

        if (info.phoneNumber.isBlank()) {
            errors["phoneNumber"] = "Phone number is required"
        } else if (info.phoneNumber.length < 7) {
            errors["phoneNumber"] = "Enter a valid phone number"
        }

        if (info.countryRegion.isBlank()) errors["countryRegion"] = "Country/Region is required"
        if (info.address.isBlank()) errors["address"] = "Address is required"
        if (info.city.isBlank()) errors["city"] = "City is required"
        if (info.state.isBlank()) errors["state"] = "State is required"

        if (info.postalCode.isBlank()) {
            errors["postalCode"] = "Postal code is required"
        } else if (info.postalCode.length < 3) {
            errors["postalCode"] = "Enter a valid postal code"
        }

        return errors
    }

    fun validatePayment(info: PaymentInfo): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        if (info.cardholderName.isBlank()) errors["cardholderName"] = "Cardholder name is required"

        val digitsOnly = info.cardNumber.filter { it.isDigit() }
        if (digitsOnly.isBlank()) {
            errors["cardNumber"] = "Card number is required"
        } else if (digitsOnly.length !in 13..19) {
            errors["cardNumber"] = "Enter a valid card number"
        }

        if (info.expiryDate.isBlank()) {
            errors["expiryDate"] = "Expiry date is required"
        } else if (!info.expiryDate.matches(Regex("^(0[1-9]|1[0-2])/\\d{2}$"))) {
            errors["expiryDate"] = "Use MM/YY format"
        }

        if (info.cvv.isBlank()) {
            errors["cvv"] = "CVV is required"
        } else if (!info.cvv.matches(Regex("^\\d{3,4}$"))) {
            errors["cvv"] = "Enter a valid CVV"
        }

        return errors
    }
}