package com.example.evvolitm.domain.model


data class ProductImage(
    val id: String,
    val productId: String,
    val imageUrl: String,
    val thumbnailUrl: String,
    val description: String? = null
)

