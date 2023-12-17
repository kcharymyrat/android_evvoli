package com.example.evvolitm.data.local.product

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "product_specs",
    foreignKeys = [
        ForeignKey(
            entity = ProductInDetailsEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("productId"),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ProductSpecEntity(
    @PrimaryKey val id: Int,
    val productId: String,
    val title: String,
    val titleEn: String,
    val titleRu: String,
    val content: String,
    val contentEn: String,
    val contentRu: String
)

