package com.example.evvolitm.util

sealed class Screen(val route: String) {
    object CategoriesScreen: Screen("categories") // start
    object CategoryProductsScreen: Screen("products")
    object ProductDetailScreen: Screen("product_detail")
    object CartScreen: Screen("cart")
    object OrderScreen: Screen("order")
}

