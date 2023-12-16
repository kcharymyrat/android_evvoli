package com.example.evvolitm.data.remote.respond.category_dtos

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    val id: String,
    val name: String,
    @SerializedName("name_en")
    val nameEn: String,
    @SerializedName("name_ru")
    val nameRu: String,
    val slug: String,
    val description: String?,
    @SerializedName("description_en")
    val descriptionEn: String?,
    @SerializedName("description_ru")
    val descriptionRu: String?,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String
)
