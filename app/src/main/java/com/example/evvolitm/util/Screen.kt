package com.example.evvolitm.util

sealed class Screen(val route: String) {
    object EvvoliAndVelutoScreen: Screen("index") // start
    object CategoriesScreen: Screen("categories")
    object CategoryProductsScreen: Screen("products")
    object SearchProductsScreen: Screen("search")
    object ProductDetailScreen: Screen("product_detail")
    object CartScreen: Screen("cart")
    object OrderScreen: Screen("order")
    object SuccessOrderScreen: Screen("successOrder")
    object AboutScreen: Screen("about")
    object SettingsScreen: Screen("settings")
}

