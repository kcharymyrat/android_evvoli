package com.example.evvolitm.data.local.category

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class CategoryEntity(
    @PrimaryKey val id: String,
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