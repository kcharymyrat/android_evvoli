package com.example.evvolitm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CategoryResponse(
    @SerialName(value = "count") var count: Int? = 0,
    @SerialName(value = "next") var next: String? = null,
    @SerialName(value = "previous") var previous: String? = null,
    @SerialName(value = "results") var results: List<Category>
)

@Serializable
data class Category(
    val id: String,
    val name: String,
    @SerialName(value = "name_en")
    val nameEn: String,
    @SerialName(value = "name_ru")
    val nameRu: String,
    val slug: String,
    val description: String?,
    @SerialName(value = "description_en")
    val descriptionEn: String?,
    @SerialName(value = "description_ru")
    val descriptionRu: String?,
    @SerialName(value = "image_url")
    val imageUrl: String,
    @SerialName(value = "thumbnail_url")
    val thumbnailUrl: String
)
