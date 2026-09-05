package com.godark14.pensel.data.mock

import com.godark14.pensel.data.model.Product

object MockProductRepository {

    val products = listOf(
        Product(
            id = "123ABC",
            name = "Canvas 50x70 cm, with white frame",
            imageUrl = "https://picsum.photos/seed/pensel1/400",
            price = 599.0,
            description = "A vibrant abstract piece blending warm terracotta tones with a striking teal focal point. Hand-finished with a white wooden frame, ready to hang."
        ),
        Product(
            id = "132CBA",
            name = "Canvas 70x50",
            imageUrl = "https://picsum.photos/seed/pensel2/400",
            price = 399.0,
            description = "A dynamic mixed-media composition inspired by natural textures and metallic accents. Unframed, printed on premium canvas."
        ),
        Product(
            id = "145DEF",
            name = "Canvas 60x60, with black frame",
            imageUrl = "https://picsum.photos/seed/pensel3/400",
            price = 449.0,
            description = "A bold geometric design in deep tones, framed in matte black for a modern gallery look."
        ),
        Product(
            id = "156GHI",
            name = "Canvas 40x60",
            imageUrl = "https://picsum.photos/seed/pensel4/400",
            price = 299.0,
            description = "A soft, minimalist landscape rendered in muted pastels. Lightweight and easy to hang anywhere."
        ),
        Product(
            id = "167JKL",
            name = "Canvas 80x60, with white frame",
            imageUrl = "https://picsum.photos/seed/pensel5/400",
            price = 699.0,
            description = "A large-format statement piece featuring layered brushwork and rich color contrast."
        ),
        Product(
            id = "178MNO",
            name = "Canvas 50x50",
            imageUrl = "https://picsum.photos/seed/pensel6/400",
            price = 349.0,
            description = "A square canvas exploring texture and light through a monochromatic palette."
        )
    )

    fun getProductById(id: String): Product? = products.find { it.id == id }

    const val SHIPPING_COST = 129.0
}