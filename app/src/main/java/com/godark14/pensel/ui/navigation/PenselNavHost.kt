package com.godark14.pensel.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.godark14.pensel.data.mock.MockProductRepository
import com.godark14.pensel.fold.FoldPosture
import com.godark14.pensel.ui.cart.CartViewModel
import com.godark14.pensel.ui.catalog.CatalogScreen
import com.godark14.pensel.ui.catalog.ProductDetailScreen
import com.godark14.pensel.ui.checkout.CheckoutScreen
import com.godark14.pensel.ui.home.HomeScreen

private object Routes {
    const val HOME = "home"
    const val CATALOG = "catalog"
    const val PRODUCT_DETAIL = "productDetail/{productId}"
    const val CHECKOUT = "checkout"

    fun productDetail(productId: String) = "productDetail/$productId"
}

@Composable
fun PenselNavHost(
    foldPosture: FoldPosture,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val cartViewModel: CartViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
        modifier = modifier
    ) {
        composable(Routes.HOME) {
            val cartItems by cartViewModel.cartItems.collectAsState()
            val itemCount = cartItems.sumOf { it.quantity }

            HomeScreen(
                featuredProducts = MockProductRepository.featuredProducts,
                foldPosture = foldPosture,
                cartItemCount = itemCount,
                onProductClick = { product ->
                    navController.navigate(Routes.productDetail(product.id))
                },
                onShopAllClick = {
                    navController.navigate(Routes.CATALOG)
                },
                onCartClick = {
                    navController.navigate(Routes.CHECKOUT)
                }
            )
        }

        composable(Routes.CATALOG) {
            val cartItems by cartViewModel.cartItems.collectAsState()
            val itemCount = cartItems.sumOf { it.quantity }

            CatalogScreen(
                products = MockProductRepository.products,
                foldPosture = foldPosture,
                cartItemCount = itemCount,
                onProductClick = { product ->
                    navController.navigate(Routes.productDetail(product.id))
                },
                onAddToCart = { product ->
                    cartViewModel.addToCart(product)
                },
                onCartClick = {
                    navController.navigate(Routes.CHECKOUT)
                }
            )
        }

        composable(
            route = Routes.PRODUCT_DETAIL,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val product = MockProductRepository.getProductById(productId ?: "")

            if (product != null) {
                ProductDetailScreen(
                    product = product,
                    foldPosture = foldPosture,
                    onBack = { navController.popBackStack() },
                    onAddToCart = { p, quantity ->
                        cartViewModel.addToCart(p, quantity)
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(Routes.CHECKOUT) {
            CheckoutScreen(
                cartViewModel = cartViewModel,
                onBack = { navController.popBackStack() }
            )
        }
    }
}