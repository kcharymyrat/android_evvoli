package com.example.evvolitm.data.local.product

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.evvolitm.data.local.category.CategoryEntity

@Entity(
    tableName = "products",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductInDetailsEntity(
    @PrimaryKey val id: String,
    val categoryId: String,
    val type: String,
    val typeEn: String,
    val typeRu: String,
    val model: String,
    val title: String,
    val titleEn: String,
    val titleRu: String,
    val slug: String,
    val price: Double,
    val salePercent: Int = 0,
    val salePrice: Double,
    val onSale: Boolean,
    val description: String?,
    val descriptionEn: String?,
    val descriptionRu: String?,
    val imageUrl: String,
    val thumbnailUrl: String,
    val video: String? = null
)
