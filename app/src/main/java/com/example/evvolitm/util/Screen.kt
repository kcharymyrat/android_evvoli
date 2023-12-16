package com.example.evvolitm.util

sealed class Screen(val route: String) {
    object Categories: Screen("categories") // start
    object CategoryProducts: Screen("products")
    object ProductDetail: Screen("product_detail")
    object Cart: Screen("cart")
    object Order: Screen("order")
}

