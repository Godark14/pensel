package com.godark14.pensel.data.model

data class PaymentInfo(
    val cardholderName: String = "",
    val cardNumber: String = "",
    val expiryDate: String = "",
    val cvv: String = ""
)