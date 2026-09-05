package com.godark14.pensel.data.model

data class Product(
    val id: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    val description: String = "",
    val currency: String = "$",
    val isFeatured: Boolean = false
)