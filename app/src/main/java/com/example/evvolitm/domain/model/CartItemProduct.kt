package com.example.evvolitm.domain.model

data class CartItemProduct(
    val id: String,
    val title: String,
    val titleEn: String,
    val titleRu: String,
    val model: String,
    val slug: String,
    val imageUrl: String?,
    val price: Double,
    val salePrice: Double
)
