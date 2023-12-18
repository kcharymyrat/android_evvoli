package com.example.evvolitm.presentation

sealed class ProductDetailScreenEvents {
    data class Refresh(val type: String) : ProductDetailScreenEvents()
}
