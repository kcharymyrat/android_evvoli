package com.example.evvolitm.domain.model

data class ProductDetail(
    val id: String,
    val categoryId: String,
    val type: String,
    val typeEn: String,
    val typeRu: String,
    val model: String,
    val title: String,
    val titleEn: String,
    val titleRu: String,
    val slug: String,
    val price: String,
    val salePercent: Int,
    val salePrice: String,
    val onSale: Boolean,
    val description: String?,
    val descriptionEn: String?,
    val descriptionRu: String?,
    val imageUrl: String,
    val thumbnailUrl: String,
    val videoUrl: String?,
    val images: List<ProductImage>,
    val specs: List<ProductSpec>
)
