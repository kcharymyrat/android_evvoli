package com.example.evvolitm.domain.model

data class Category(
    val id: String,
    val name: String,
    val nameEn: String,
    val nameRu: String,
    val slug: String,
    val description: String? = null,
    val descriptionEn: String? = null,
    val descriptionRu: String? = null,
    val imageUrl: String,
    val thumbnailUrl: String,
)