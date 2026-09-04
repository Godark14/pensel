package com.godark14.pensel.data.mock

import com.godark14.pensel.data.model.CartItem
import com.godark14.pensel.data.model.Product

object MockProductRepository {

    val products = listOf(
        Product(
            id = "123ABC",
            name = "Canvas 50x70 cm, with white frame",
            imageUrl = "https://picsum.photos/seed/pensel1/200",
            price = 599.0
        ),
        Product(
            id = "132CBA",
            name = "Canvas 70x50",
            imageUrl = "https://picsum.photos/seed/pensel2/200",
            price = 399.0
        )
    )

    fun getMockCart(): List<CartItem> = products.map { CartItem(product = it, quantity = 1) }

    const val SHIPPING_COST = 129.0
}